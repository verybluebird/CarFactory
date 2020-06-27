package com.company.store;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Configuration {
    private final HashMap<String, Integer> status;

    public Configuration (String fileName) {
        status = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)) ){
        String line;
        while ((line=reader.readLine())!= null){
            StringBuilder key = new StringBuilder();
            int position = 0;
            for (int i = 0; i < line.length() ; i++) {
                if (line.charAt(i)=='='){
                    position=i;
                    break;
                } else key.append(line.charAt(i));
            }
        StringBuilder value  = new StringBuilder();
            for (int i = position+1; i <line.length() ; i++) {
                value.append(line.charAt(i));
            }
            status.put(key.toString(),Integer.valueOf(value.toString()));
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getValue (String key) {
        return status.get(key);
    }
}
