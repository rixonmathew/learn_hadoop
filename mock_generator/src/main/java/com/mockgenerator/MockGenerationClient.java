package com.mockgenerator;

import com.mockgenerator.configuration.Schema;
import com.mockgenerator.configuration.SchemaParser;
import com.mockgenerator.generator.FileGenerator;
import com.mockgenerator.generator.Options;

import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 22/1/13
 * Time: 4:24 PM
 * This class is the client for generating mock files
 */
public class MockGenerationClient {

    public static void main(String[] args) throws IOException {
        //TODO create the options and start the job
        if(args.length!=2) {
            System.err.println("Usage java jar MockGenerator --file <properties.file>");
            return;
        }
        String inputPropertiesFileName = args[1];
        Properties properties = loadProperties(inputPropertiesFileName);
        Schema schema = createSchema(properties);
        Options options = createOptions(properties);
        FileGenerator fileGenerator = new FileGenerator(options,schema);
        fileGenerator.generateFiles();
    }

    private static Properties loadProperties(String inputPropertiesFileName) throws IOException {
        FileReader propertiesFileReader = new FileReader(inputPropertiesFileName);
        Properties properties = new Properties();
        properties.load(propertiesFileReader);
        return properties;
    }

    private static Schema createSchema(Properties properties) {
        String schemaName = properties.getProperty("schema");
        return SchemaParser.parse(schemaName);
    }

    private static Options createOptions(Properties properties) {
        Options options = new Options();
        options.setGenerationType(properties.getProperty("generationType"));
        options.setNumberOfFileSplits(Integer.valueOf(properties.getProperty("numberOfSplits")));
        options.setOutputDirectory(properties.getProperty("outputDirectory"));
        options.setNumberOfRecordsPerSplit(Integer.valueOf(properties.getProperty("recordsPerSplit")));
        options.setCompressOutput(Boolean.valueOf(properties.getProperty("compressOutput")));
        return options;
    }
}
