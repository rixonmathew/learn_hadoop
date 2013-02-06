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

    private static final String DEFAULT_GENERATION_TYPE = "random";
    private static final int DEFAULT_NUMBER_OF_SPLITS = 5;
    private static final String DEFAULT_OUTPUT_DIR = "output_"+System.currentTimeMillis();
    private static final long DEFAULT_RECORDS_PER_SPLIT = 10000;
    private static final String SCHEMA = "schema";
    private static final String RECORDS_PER_SPLIT = "recordsPerSplit";
    private static final String OUTPUT_DIRECTORY = "outputDirectory";
    private static final String NUMBER_OF_SPLITS = "numberOfSplits";
    private static final String GENERATION_TYPE = "generationType";

    public static void main(String[] args) throws IOException {
        //TODO create the options and start the job
        if(args.length!=2) {
            System.err.println("Usage java jar MockGenerator --file <properties.file>");
            return;
        }
        String inputPropertiesFileName = args[1];
        Properties properties = loadProperties(inputPropertiesFileName);
        boolean isValid = checkProperties(properties);
        if(!isValid){
            System.err.println("All required properties are not specified.Please check the property file:"+inputPropertiesFileName);
            return;
        }

        Schema schema = createSchema(properties);
        Options options = createOptions(properties);
        FileGenerator fileGenerator = new FileGenerator(options,schema);
        fileGenerator.generateFiles();
    }

    private static boolean checkProperties(Properties properties) {
        boolean valid = true;
        String schemaFileName = properties.getProperty(SCHEMA);
        if(schemaFileName==null||schemaFileName.isEmpty()){
            valid = false;
        }
        File file = new File(schemaFileName);
        if (!file.exists()||!file.canRead()){
            valid = false;
        }
        return valid;
    }

    private static Properties loadProperties(String inputPropertiesFileName) throws IOException {
        FileReader propertiesFileReader = new FileReader(inputPropertiesFileName);
        Properties properties = new Properties();
        properties.load(propertiesFileReader);
        return properties;
    }

    private static Schema createSchema(Properties properties) {
        String schemaName = properties.getProperty(SCHEMA);
        return SchemaParser.parse(schemaName);
    }

    private static Options createOptions(Properties properties) {
        Options options = new Options();
        setGenerationType(properties, options);
        setNumberOfSplits(properties, options);
        setOutputDirectory(properties, options);
        setRecordCount(properties, options);
        return options;
    }

    private static void setRecordCount(Properties properties, Options options) {
        String recordCount = properties.getProperty(RECORDS_PER_SPLIT);
        long numberOfRecords;
        if(recordCount==null||recordCount.isEmpty()){
            numberOfRecords = DEFAULT_RECORDS_PER_SPLIT;
        } else {
            numberOfRecords = Long.valueOf(recordCount);
        }
        options.setNumberOfRecordsPerSplit(numberOfRecords);
    }

    private static void setOutputDirectory(Properties properties, Options options) {
        String outputDirectory = properties.getProperty(OUTPUT_DIRECTORY);
        if(outputDirectory==null||outputDirectory.isEmpty()){
            outputDirectory = DEFAULT_OUTPUT_DIR;
        }
        options.setOutputDirectory(outputDirectory);
    }

    private static void setNumberOfSplits(Properties properties, Options options) {
        String splits = properties.getProperty(NUMBER_OF_SPLITS);
        int numberOfSplits;
        if(splits==null || splits.isEmpty()){
            numberOfSplits = DEFAULT_NUMBER_OF_SPLITS;
        } else {
            numberOfSplits = Integer.valueOf(splits);
        }
        options.setNumberOfFileSplits(numberOfSplits);
    }

    private static void setGenerationType(Properties properties, Options options) {
        String generationType = properties.getProperty(GENERATION_TYPE);
        if (generationType==null||generationType.isEmpty()) {
            generationType= DEFAULT_GENERATION_TYPE;
        }
        options.setGenerationType(generationType);
    }
}
