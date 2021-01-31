package com.example.warehouse.manager.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MultipartFileReader {
    public static String saveFileAndGetContent(MultipartFile file) throws IOException {

        Path tempFile = Files.createTempFile(null, null);
//        System.out.println(tempFile);

        // write a line
        Files.write(tempFile, file.getBytes());

        // read a temp file
        String tempFileContent = Files.readString(tempFile);
        return tempFileContent;
    }
}
