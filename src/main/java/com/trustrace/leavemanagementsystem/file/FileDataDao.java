package com.trustrace.leavemanagementsystem.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Repository
public class FileDataDao {
    @Autowired
    private MongoTemplate mt;

    @Value("${upload.path}")
    private String dirPath;

    public FileData uploadFile(MultipartFile file) throws Exception {
        String filePath = dirPath + file.getOriginalFilename();
        FileData fileData = mt.save(FileData.builder()
                        .fileName(file.getOriginalFilename())
                        .filetype(file.getContentType())
                        .filePath(filePath)
                .build());
        file.transferTo(new File(filePath));
        return fileData;
    }

    public FileData downloadFile(String id) throws IOException {
        FileData fileData = mt.findById(id, FileData.class);
        return fileData;
    }

    public FileData changeExistingFile(String fileId, MultipartFile img) throws Exception {
        FileData fileData = mt.findById(fileId, FileData.class);
        if (fileData == null) throw new RuntimeException("File Not found in FileDataDao");

        FileData newFile = uploadFile(img);
        if(newFile == null) throw new RuntimeException("File Not Saved in FileDataDao");

        File existingFile = new File(fileData.getFilePath());
        if (existingFile.exists()) {
            existingFile.delete();
        }
        mt.remove(fileData);
        return newFile;
    }

    public boolean deleteFile(String id) {
        FileData fileData = mt.findById(id, FileData.class);
        if(fileData == null) return false;
        File file = new File(fileData.getFilePath());
        if(file.exists()) file.delete();
        mt.remove(fileData);
        return true;
    }
}
