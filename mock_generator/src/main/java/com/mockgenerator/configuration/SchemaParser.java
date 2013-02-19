package com.mockgenerator.configuration;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;

/**
 * User: rixonmathew
 * Date: 19/01/13
 * Time: 1:30 PM
 * This class represents the configuration
 */
public class SchemaParser {

    private static final Logger LOG = Logger.getLogger(SchemaParser.class);
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
            LOG.error("An empty json was specified to SchemaParser");
            return Schema.emptySchema();
        }
        return JSON.parseObject(jsonString,Schema.class);
    }
}
