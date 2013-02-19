package com.mockgenerator.strategy.record;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 25/1/13
 * Time: 10:37 AM
 * This class is context for various strategies for creating a Record
 */
public class RecordCreationContext {

    private static final Map<String,RecordCreationStrategy> recordCreationStrategies;

    private static final String DELIMITED = "delimited";
    private static final String FIXED_WIDTH = "fixed-width";

    static {
        recordCreationStrategies = new HashMap<String, RecordCreationStrategy>();
        recordCreationStrategies.put(DELIMITED,new DelimitedRecordCreationStrategy());
        recordCreationStrategies.put(FIXED_WIDTH,new FixedWidthRecordCreationStrategy());
    }


    public static RecordCreationStrategy strategyFor(String recordType) {
        return recordCreationStrategies.get(recordType);
    }
}
