package com.trustrace.leavemanagementsystem.file;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
@Data
@Builder
public class FileData {
    @Id
    private String id;
    private String fileName;
    private String filetype;
    private String filePath;
}
