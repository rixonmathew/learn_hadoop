package com.mockgenerator.strategy;

import com.mockgenerator.configuration.Schema;
import com.mockgenerator.generator.Options;
import com.mockgenerator.strategy.record.RecordCreationContext;
import com.mockgenerator.strategy.record.RecordCreationStrategy;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
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
    private final Map<Long, File> filesForSplit;
    private RecordCreationStrategy recordCreationStrategy;

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
        if (options.getOutputDirectory() == null || options.getOutputDirectory().isEmpty()) {
            String name = "output_" + System.currentTimeMillis();
            outputDirectory = new File(name);
        } else {
            outputDirectory = new File(options.getOutputDirectory());
        }

        outputDirectory.mkdirs();

    }

    private void generateMockFiles() throws IOException {
        createFilesForSplits();
        populateFilesWithRandomData();
    }

    private void createFilesForSplits() throws IOException {
        String fileName = schema.getName() + "-part";
        for (int split = 0; split < options.getNumberOfFileSplits(); split++) {
            String splitFileName = fileName + "-" + split;
            File outputFile = new File(outputDirectory.getAbsolutePath() + File.separator + splitFileName);
            outputFile.createNewFile();
            filesForSplit.put((long) split, outputFile);
        }
    }

    private void populateFilesWithRandomData() {
        for (Long split : filesForSplit.keySet()) {
            try {
                populateDataForSplit(split);
            } catch (IOException e) {
                LOG.error("An exception occurred while trying to populate data for split");
                e.printStackTrace();
            }
        }
    }

    private void populateDataForSplit(Long split) throws IOException{
        File outputFile = filesForSplit.get(split);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        recordCreationStrategy = RecordCreationContext.strategyFor(schema.getType());
        long maxRecords = options.getNumberOfRecordsPerSplit();
        for (int i = 0; i < maxRecords; i++) {
            String record = recordCreationStrategy.createRecord(schema, options, i);
            writer.write(record);
            writer.newLine();
        }
        writer.close();
    }
}