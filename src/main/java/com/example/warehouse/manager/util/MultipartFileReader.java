package com.example.warehouse.manager.util;

import com.example.warehouse.manager.domain.InventoryStock;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Event;
import org.springframework.boot.json.JsonParser;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
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

    public static void JsonToJavaObject(MultipartFile dataFile) throws IOException {

        Path tempFile = Files.createTempFile(null, null);
        Files.write(tempFile, dataFile.getBytes());
        dataFile.transferTo(tempFile);

        File file = new File(String.valueOf(tempFile.toFile())).getAbsoluteFile();

        new JsonArrayStreamDataSupplier<>(file, InventoryStock.class) //Got the Stream
                .forEachRemaining(nightsRest -> {
                    System.out.println(nightsRest.toString());
                });
    }

/*    public static void JsonpStreaming(MultipartFile dataFile){
//    private ObjectMapper Json;
    final JsonParser parser = Json.createParser(new StringReader(result));
    String key = null;
    String value = null;
    while (parser.hasNext()) {
        final Event event = parser.next();
        switch (event) {
            case KEY_NAME:
                key = parser.getString();
                System.out.println(key);
                break;
            case VALUE_STRING:
                value = parser.getString();
                System.out.println(value);
                break;
        }
    }
    parser.close();
}*/
}
