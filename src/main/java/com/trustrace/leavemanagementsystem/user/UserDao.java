package com.trustrace.leavemanagementsystem.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private MongoTemplate mt;

    public User getUserByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        return mt.findOne(query, User.class);
    }

    public List<User> getAllUsers() {
        return mt.findAll(User.class);
    }

    public User getUserById(String id) {
        return mt.findById(id, User.class);
    }

    public User createUser(User user) {
        return mt.save(user);
    }

    public boolean deleteUserById(String id) {
        User user = getUserById(id);
        if(user == null) {
            return false;
        }
        mt.remove(user);
        return true;
    }

    public Page<User> getUsersOfPage(int page, int size) {
        Query query = new Query();
        long totalCount = mt.count(query, User.class);

        Pageable pageable = PageRequest.of(page, size);
        query.with(pageable);

        List<User> responses = mt.find(query, User.class);

        return new PageImpl<>(responses, pageable, totalCount);
    }
}
