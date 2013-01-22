package com.mockgenerator.strategy;

import com.mockgenerator.configuration.Field;
import com.mockgenerator.configuration.Schema;
import com.mockgenerator.generator.Options;
import com.mockgenerator.provider.TypeValueProviders;
import com.mockgenerator.provider.ValueProvider;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Arrays;
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
    private final Map<Long,File> filesForSplit;

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
        String fileName = schema.getName()+"-part";
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
            e.printStackTrace();
        }

        long maxRecords = options.getNumberOfRecordsPerSplit();
        List<Field> fields = schema.getFields();
        String separator = schema.getSeparator();
        for (int i=0;i<maxRecords;i++){
            StringBuilder record = new StringBuilder();
            for (int j=0;j<fields.size();j++) {
                Field field = fields.get(j);
                ValueProvider valueProvider = TypeValueProviders.valueProviderFor(field.getType());
                Object value;
                //TODO This has become ugly. How to simplify this
                if (field.getRange()!=null) {
                    List<String> values = Arrays.asList(field.getRange().split(","));
                    value = valueProvider.randomValueFromRange(values);
                } else {
                    if (field.getFormatMask()!=null) {
                        value = valueProvider.formattedRandomValue(field.getMinLength(),field.getMaxLength(),field.getFormatMask());
                    } else {
                        value = valueProvider.randomValue(field.getMinLength(),field.getMaxLength());
                    }
                }
                record.append(value);
                if (j!=fields.size()-1){
                    record.append(separator);
                }
            }
            try {
                writer.write(record.toString());
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
