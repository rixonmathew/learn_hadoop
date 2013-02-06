package com.mockgenerator.configuration;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.URL;

/**
 * User: rixonmathew
 * Date: 19/01/13
 * Time: 1:30 PM
 * This class represents the configuration
 */
public class SchemaParser {

    public static Schema parse(String configurationFileName) {
        Schema schema = null;
        try {
            String jsonString = readInputFile(configurationFileName);
            schema = populateAttributes(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schema;

    }

    private static String readInputFile(String configurationFileName) throws IOException {
//        final ClassLoader resourceLoader = Thread.currentThread().getContextClassLoader();
//        InputStream inputStream = resourceLoader.getResourceAsStream(configurationFileName);
//        if (inputStream==null){
//            throw new FileNotFoundException("Could not load configuration file:"+configurationFileName);
//        }
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        BufferedReader br = new BufferedReader(new FileReader(configurationFileName));
        StringBuilder stringBuffer = new StringBuilder();
        String line = br.readLine();
        stringBuffer.append(line);
        while (line != null) {
            line = br.readLine();
            if (line != null)
                stringBuffer.append(line);
        }
        br.close();
        return stringBuffer.toString();
    }

    private static Schema populateAttributes(String jsonString) {
        if(jsonString==null|| jsonString.isEmpty()){
            return null; //TODO add log statements and introduce the concept of null Schema
        }
        return JSON.parseObject(jsonString,Schema.class);
    }
}
