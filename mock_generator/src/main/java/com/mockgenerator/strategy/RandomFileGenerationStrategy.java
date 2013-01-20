package com.mockgenerator.strategy;

import com.mockgenerator.configuration.Field;
import com.mockgenerator.configuration.Schema;
import com.mockgenerator.generator.Options;
import com.mockgenerator.provider.TypeValueProviders;
import com.mockgenerator.provider.ValueProvider;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 8:33 PM
 */
public class RandomFileGenerationStrategy implements FileGenerationStrategy {

    private final Logger LOG = Logger.getLogger(RandomFileGenerationStrategy.class);
    private Schema schema;
    private Options options;
    private File outputDirectory;
    private Map<Long,File> filesForSplit;

    public RandomFileGenerationStrategy() {
        filesForSplit = new HashMap<Long, File>();
    }

    @Override
    public void generateFileData(Schema schema, Options options) {
        /**
         * 1) Create the output directory
         * 2) Determine the number of splits required and create files as per the splits and options provied
         * 3) Based on the schema fields initialize the value providers
         * 4) Create Records by iterating through the fields
         * 5) Output the records to the files
         */
        this.schema = schema;
        this.options = options;
        generateOutputDirectories();
        try {
            generateMockFiles();
        } catch (IOException e) {
            LOG.error("An error occurred while creating output data");
            e.printStackTrace();
        }

    }

    private void generateOutputDirectories() {
        outputDirectory = new File(options.getOutputDirectory());
        outputDirectory.mkdirs();

    }

    private void generateMockFiles() throws IOException {
        createFilesForSplits();
        populateFilesWithRandomData();
    }

    private void createFilesForSplits() throws IOException {
        String fileName = schema.getName()+"-part-";
        for (int split = 0;split<options.getNumberOfFileSplits();split++){
            String splitFileName = fileName+"-"+split;
            File outputFile = new File(outputDirectory.getAbsolutePath()+File.separator+splitFileName);
            outputFile.createNewFile();
            filesForSplit.put((long)split,outputFile);
        }
    }

    private void populateFilesWithRandomData() {
        for (Long split:filesForSplit.keySet()) {
            populateDataForSplit(split);
        }
    }

    private void populateDataForSplit(Long split) {

        File outputFile = filesForSplit.get(split);
        BufferedWriter writer = null;
        try {
           writer  =  new BufferedWriter(new FileWriter(outputFile));
        } catch (FileNotFoundException e) {
            LOG.error("An error occurred while accessing stream for split:"+split);
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        long maxRecords = options.getNumberOfRecordsPerSplit();
        List<Field> fields = schema.getFields();
        String separator = schema.getSeparator();
        for (int i=0;i<maxRecords;i++){
            StringBuffer record = new StringBuffer();
            for(Field field:fields) {
                ValueProvider valueProvider = TypeValueProviders.valueProviderFor(field.getType());
                Object value = valueProvider.randomValue(field.getMinLength(),field.getMaxLength());
                record.append(value).append(separator);
            }
            try {
                writer.write(record.toString());
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
