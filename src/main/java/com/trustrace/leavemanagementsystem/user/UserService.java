package com.trustrace.leavemanagementsystem.user;

import com.trustrace.leavemanagementsystem.file.FileData;
import com.trustrace.leavemanagementsystem.file.FileDataDao;
import com.trustrace.leavemanagementsystem.password.MailDao;
import com.trustrace.leavemanagementsystem.password.MailService;
import com.trustrace.leavemanagementsystem.password.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;
    @Autowired
    private MailDao mailDao;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDao dao;
    @Autowired
    private FileDataDao fileDataDao;

    public User getUserByEmail(String email) {
        User user = dao.getUserByEmail(email);
        if(user == null) throw new UsernameNotFoundException("User not found");
        return user;
    }

    public List<UserDto> convertListOfUsers(List<User> users) {
        List<UserDto> finalUsers = new ArrayList<>();
        for (User user: users){
            User manager = dao.getUserById(user.getManagerId());
            finalUsers.add(
                    userMapper.toUserDto(user, manager)
            );
        }
        return finalUsers;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = dao.getAllUsers();
        return convertListOfUsers(users);
    }

    public UserDto getUserById(String id) {
        User user =  dao.getUserById(id);
        User manager = dao.getUserById(user.getManagerId());
        return userMapper.toUserDto(user, manager);
    }

    public UserDto createUser(User user) {
        user.setPassword(passwordEncoder.encode((user.getPassword())));
        user.setMyFiles(new HashMap<String, String>());
        User newUser = dao.createUser(user);
        User manager = dao.getUserById(user.getManagerId());
        return userMapper.toUserDto(newUser, manager);
    }

    public boolean deleteUser(String id) {
        return dao.deleteUserById(id);
    }

    public Page<UserDto> getUsersOfPage(int page, int size) {
        Page<User> users = dao.getUsersOfPage(page, size);
        List<UserDto> userList = convertListOfUsers(users.getContent());
        return new PageImpl<>(userList, users.getPageable(), users.getTotalElements());
    }

    public String changeProfile(String id, MultipartFile img) throws Exception {
        User user = dao.getUserById(id);
        Map<String, String> files = user.getMyFiles();
        String fileId = files.get("profile");
        FileData fileData = null;

        if(fileId == null){
            fileData = fileDataDao.uploadFile(img);
        }
        else {
            fileData = fileDataDao.changeExistingFile(fileId, img);
        }
        if (fileData != null){
            files.put("profile", fileData.getId());
            dao.saveUser(user);
            return fileData.getId();
        }
        else {
            throw new RuntimeException("File not saved");
        }
    }

    public List<ManagerDto> searchUserByName(String name) {
        List<User> users = dao.searchUserByName(name);
        List<ManagerDto> filteredUsers = new ArrayList<>();

        for(User user: users) {
            filteredUsers.add(new ManagerDto(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getDesignation(),
                    user.getDepartment()
            ));
        }
        return filteredUsers;
    }

    public boolean requestPasswordReset(String email) {
        User user = getUserByEmail(email);
        if(user == null) return false;
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, user.getId(), LocalDateTime.now().plusMinutes(15));
        mailDao.save(resetToken);

        String resetLink = "http://localhost:4200/reset-password?token=" + token;
        mailService.sendEmail(user.getEmail(), "Reset Your Password", "Click the link to reset: " + resetLink);

        return true;
    }

    public boolean validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> resetToken = mailDao.findByToken(token);
        return resetToken.isPresent() && !resetToken.get().isExpired();
    }

    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> resetToken = mailDao.findByToken(token);
        if (resetToken.isEmpty() || resetToken.get().isExpired()) return false;

        User user = dao.getUserById(resetToken.get().getUserId());
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        dao.saveUser(user);
        mailDao.delete(token);

        return true;
    }
}
