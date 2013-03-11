package com.mockgenerator.strategy.marketdata;

import com.mockgenerator.configuration.Field;
import com.mockgenerator.strategy.AbstractDataGenerationStrategy;
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
public class QuoteDataGenerationStrategy extends AbstractMarketDataGenerationStrategy {

    private final String BID_PRICE = "BidPrice";
    private final String BID_SIZE = "BidSize";
    private final String ASK_PRICE = "AskPrice";
    private final String ASK_SIZE = "AskSize";

    @Override
    protected Map<Field, String> determineOverriddenValues(Long startTime, String symbolName, PriceSet priceSet) {
        Map<Field,String> overriddenValues = new HashMap<Field, String>();
        Field symbolField = fieldMap.get(SYMBOL);
        overriddenValues.put(symbolField,symbolName);
        Field timestampField = fieldMap.get(TIMESTAMP);
        String timeStamp = getValueWithPadding(Long.toString(startTime),timestampField);
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
}
