package com.mockgenerator.strategy.record;

import com.mockgenerator.configuration.Schema;
import com.mockgenerator.generator.Options;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 25/1/13
 * Time: 10:28 AM
 * This interface implements a strategy for creating records based on file type ( delimited,fixedWidht).
 *
 */
public interface RecordCreationStrategy {
    /**
     * This method will create a record in the file as per the file type.
     * @param schema represents the schema of the file to be generated
     * @param options represents the options for creating the file
     * @param recordCounter the recordCounter.
     * @return fully formed record
     */
    public String createRecord(Schema schema,Options options,long recordCounter);
}
