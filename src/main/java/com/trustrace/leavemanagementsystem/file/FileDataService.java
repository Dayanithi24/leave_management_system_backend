package com.trustrace.leavemanagementsystem.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileDataService {
    @Autowired
    private FileDataDao dao;

    public FileData uploadFile(MultipartFile file) throws Exception {
        return dao.uploadFile(file);
    }

    public FileData downloadFile(String id) throws IOException {
        return dao.downloadFile(id);
    }
}
