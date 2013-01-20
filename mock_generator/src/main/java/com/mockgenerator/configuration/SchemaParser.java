package com.mockgenerator.configuration;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return schema;

    }

    private static String readInputFile(String configurationFileName) throws IOException {
        URL fileURL = ClassLoader.getSystemClassLoader().getResource(configurationFileName);
        if (fileURL == null) {
            return "";
        }
        BufferedReader br = new BufferedReader(new FileReader(fileURL.getPath()));
        StringBuffer stringBuffer = new StringBuffer();
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
