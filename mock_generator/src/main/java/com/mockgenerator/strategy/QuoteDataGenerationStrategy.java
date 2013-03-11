package com.mockgenerator.strategy;

import com.mockgenerator.configuration.Field;
import com.mockgenerator.strategy.record.RecordCreationContext;
import com.mockgenerator.strategy.record.RecordCreationStrategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 7/3/13
 * Time: 6:20 PM
 * This file is used for generating volume data for quotes. This file
 */
public class QuoteDataGenerationStrategy extends AbstractDataGenerationStrategy {

    private final String SYMBOL = "Symbol";
    private final String TIMESTAMP = "Timestamp";
    private final String BID_PRICE = "BidPrice";
    private final String BID_SIZE = "BidSize";
    private final String ASK_PRICE = "AskPrice";
    private final String ASK_SIZE = "AskSize";

    private Map<String,List<PriceSet>> masterQuotes;
    protected Map<Long,List<Long>> timestampsPerSplit;
    Map<String,Field> fieldMap;
    long startTimestamp;
    long endTimestamp;
    long stepValue;

    @Override
    protected void prepareForDataGeneration() {
        createMasterQuotes();
        initializeTimeStamps();
    }

    private void createMasterQuotes() {
        populateFieldMap();
        masterQuotes = new HashMap<String, List<PriceSet>>();
        Field symbolField = fieldMap.get(SYMBOL);
        String[] symbols = symbolField.getRange().split(",");
        for (String symbolName:symbols) {
            masterQuotes.put(symbolName,createPriceSets());
        }
    }

    private void populateFieldMap() {
        fieldMap = new HashMap<String, Field>();
        for (Field field:schema.getFields()){
            fieldMap.put(field.getName(),field);
        }
    }


    private void initializeTimeStamps() {
        Field timeStampField = fieldMap.get(TIMESTAMP);
        startTimestamp = Long.valueOf(timeStampField.getMinValue());
        endTimestamp = Long.valueOf(timeStampField.getMaxValue());
        stepValue = Long.valueOf(timeStampField.getStepValue());
        populateTimestampsForSplits();
    }

    private void populateTimestampsForSplits() {
        timestampsPerSplit = new HashMap<Long, List<Long>>();
        for (int i=0;i<options.getNumberOfFileSplits();i++) {
            List<Long> timestamps = getTimestampsPerSplit(i);
            timestampsPerSplit.put(Long.valueOf(i),timestamps);
        }
    }

    private List<Long> getTimestampsPerSplit(int i) {
        Long startTime = startTimestamp+i*options.getNumberOfRecordsPerSplit()*stepValue;
        Long endTime = startTime+stepValue*options.getNumberOfRecordsPerSplit()-stepValue;
        List<Long> timestamps = new ArrayList<Long>();
        timestamps.add(startTime);
        timestamps.add(endTime);
        return timestamps;
    }


    private void generateMockData() {
    }


    @Override
    protected void populateDataForSplit(long split, String taskId) throws IOException {
        RecordCreationStrategy recordCreationStrategy = RecordCreationContext.strategyFor(schema.getType());
        List<Long> timestamps = timestampsPerSplit.get(split);
        File outputFile = filesForSplit.get(split);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        Long startTime = timestamps.get(0);
        Long endTime = timestamps.get(1);
        while (startTime<=endTime){
            for (String symbolName:masterQuotes.keySet()){
                for (PriceSet priceSet:masterQuotes.get(symbolName)) {
                    Map<Field,String> overriddenValues = determineOverriddenValues(startTime,symbolName,priceSet);
                    String record = recordCreationStrategy.createRecordWithOverrides(schema,options,0,overriddenValues);
                    writer.write(record);
                    writer.newLine();
                }
            }
            startTime+=stepValue;
            progressReporter.updateThreadProgress(taskId, (endTime-startTime)*1.0f/endTime);
        }
        writer.close();
    }


    private List<PriceSet> createPriceSets() {
        List<PriceSet> priceSets = new ArrayList<PriceSet>();
        PriceSet priceSet100 = new PriceSet();
        priceSet100.askPrice="00002000000";
        priceSet100.bidPrice="00001800000";
        priceSet100.askQuantity="0000100";
        priceSet100.bidQuantity="0000100";
        priceSets.add(priceSet100);
        PriceSet priceSet200 = new PriceSet();
        priceSet200.askPrice="00002010000";
        priceSet200.bidPrice="00001850000";
        priceSet200.askQuantity="0000200";
        priceSet200.bidQuantity="0000200";
        priceSets.add(priceSet200);
        return priceSets;
    }
    private Map<Field, String> determineOverriddenValues(long time, String symbolName, PriceSet priceSet) {
        Map<Field,String> overriddenValues = new HashMap<Field, String>();
        Field symbolField = fieldMap.get(SYMBOL);
        overriddenValues.put(symbolField,symbolName);
        Field timestampField = fieldMap.get(TIMESTAMP);
        String timeStamp = getValueWithPadding(Long.toString(time),timestampField);
        overriddenValues.put(timestampField,timeStamp);
        Field bidPrice = fieldMap.get(BID_PRICE);
        double randomBoundForPrice = 20.0d;
        double randomBoundForQuantity = 50.0d;
        String bidPriceValue = getRandomValue(priceSet.bidPrice, randomBoundForPrice,bidPrice);
        overriddenValues.put(bidPrice,bidPriceValue);
        Field bidQuantity = fieldMap.get(BID_SIZE);
        String bidQuantityValue = getRandomValue(priceSet.bidQuantity, randomBoundForQuantity,bidQuantity);
        overriddenValues.put(bidQuantity,bidQuantityValue);
        Field askPrice = fieldMap.get(ASK_PRICE);
        String askPriceValue = getRandomValue(priceSet.askPrice, randomBoundForPrice,askPrice);
        overriddenValues.put(askPrice,askPriceValue);
        Field askQuantity = fieldMap.get(ASK_SIZE);
        String askQuantityValue = getRandomValue(priceSet.askQuantity, randomBoundForQuantity,askQuantity);
        overriddenValues.put(askQuantity,askQuantityValue);
        return overriddenValues;
    }

    private String getValueWithPadding(String value,Field field) {
        int paddingLength = field.getFixedLength()-value.length();
        StringBuilder valueWithPadding = new StringBuilder();
        for (int i=0;i<paddingLength;i++) {
            valueWithPadding.append(field.getPadding());
        }
        return valueWithPadding.append(value).toString();
    }


    private String getRandomValue(String baseValue,double randomBound,Field field) {
        Random random = new Random();
        double multiplier = random.nextInt(1)==1?1.0d:-1.0d;
        BigDecimal randomValue  = BigDecimal.valueOf(Double.valueOf(baseValue)+randomBound*Math.random()*multiplier);
        randomValue =  randomValue.setScale(0, BigDecimal.ROUND_HALF_UP);
        return getValueWithPadding(randomValue.toPlainString(),field);
    }


    class PriceSet {
        String askPrice;
        String askQuantity;
        String bidPrice;
        String bidQuantity;
    }

}
