package com.worst.producer;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasLength;

@Service
@Log4j2
public class FilesServiceImpl {

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${generateFile.csvFile}")
    private String localDirectory;

    public List<String> loadFromDefaultPath() {
        return readFile(localDirectory);
    }

    public List<String> readFile(String filePath){
        List<String> result = new ArrayList<>();

        if(!hasLength(filePath)){
            return result;
        }

        try {
            Resource resource = resourceLoader.getResource(filePath);
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
