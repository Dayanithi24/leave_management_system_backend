package com.trustrace.leavemanagementsystem.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/file/")
public class FileDataController {
    @Autowired
    private FileDataService fs;

    @PostMapping("upload")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) throws Exception {
        String response = fs.uploadFile(file);
        if(response == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok(response);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> downloadFile(@PathVariable String id) throws IOException {
        FileData fileData = fs.downloadFile(id);
        if(fileData != null) {
            byte[] file = Files.readAllBytes(new File(fileData.getFilePath()).toPath());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(fileData.getFiletype()))
                    .body(file);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File notFound");
    }
}
