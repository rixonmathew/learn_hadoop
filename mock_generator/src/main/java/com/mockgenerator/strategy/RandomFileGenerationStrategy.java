package com.mockgenerator.strategy;

import com.mockgenerator.strategy.record.RecordCreationContext;
import com.mockgenerator.strategy.record.RecordCreationStrategy;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 8:33 PM
 * This is random data generation strategy. All fields in the record have random values specified in the schema
 */
public class RandomFileGenerationStrategy extends AbstractDataGenerationStrategy {

    @Override
    protected void populateDataForSplit(long split, String taskId) throws IOException {
        File outputFile = filesForSplit.get(split);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        RecordCreationStrategy recordCreationStrategy = RecordCreationContext.strategyFor(schema.getType());
        long maxRecords = options.getNumberOfRecordsPerSplit();
        for (int i = 0; i < maxRecords; i++) {
            String record = recordCreationStrategy.createRecord(schema, options, i);
            writer.write(record);
            writer.newLine();
            progressReporter.updateThreadProgress(taskId, (i + 1) * 100.0f / maxRecords);
        }

        writer.close();
    }
}