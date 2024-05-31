package com.worst.producer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasLength;

@Service
@Log4j2
public class FilesServiceImpl {

    @Value("${generateFile.localDirectory}")
    private String localDirectory;

    public List<String> readFile(String filePath){
        if(!hasLength(filePath)){
            return new ArrayList<>();
        }

        try {
            FileInputStream inputStream = new FileInputStream(filePath);

            return read(inputStream);
        }catch (Exception exception){
            log.atInfo().log("Failed to read file at path: " + filePath);
            log.atError().log(exception.getMessage());

            return new ArrayList<>();
        }
    }

    private List<String> read(InputStream inputStream){
        List<String> result = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))){
            String line = bufferedReader.readLine();
            while (line != null) {
                result.add(line);

                line = bufferedReader.readLine();
            }
        } catch (IOException exception){
            log.atInfo().log("Failed to read file.", " Detail: ", exception.getMessage());
            log.atError().log(exception.getMessage());
        }

        return result;
    }

    public List<String> loadFromDefaultPath() {
        return readFile(localDirectory);
    }
}
