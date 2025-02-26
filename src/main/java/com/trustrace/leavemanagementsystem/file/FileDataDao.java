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

    public String uploadFile(MultipartFile file) throws Exception {
        String filePath = dirPath + file.getOriginalFilename();
        FileData fileData = mt.save(FileData.builder()
                        .fileName(file.getOriginalFilename())
                        .filetype(file.getContentType())
                        .filePath(filePath)
                .build());
        file.transferTo(new File(filePath));
        if(fileData != null) {
            return "File uploaded Successfully " + fileData.getId();
        }
        return null;
    }

    public FileData downloadFile(String id) throws IOException {
        return mt.findById(id, FileData.class);
    }
}
