package com.mockgenerator.provider;

import java.util.Random;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 9:42 PM
 */
public class IDValueProvider extends AbstractValueProvider<Long> {

    @Override
    public Long randomValue() {
        return Math.abs(random.nextLong());
    }

    @Override
    Long formatValueFromString(String stringValue) {
        return Long.valueOf(stringValue);
    }

    @Override
    public Long randomValue(long minLength, long maxLength) {
        long value;
        long minimumValue,maxValue;
        maxValue = (long)Math.pow(10,maxLength)-1;
        minimumValue = (long)Math.pow(10,(minLength-1))+1;
        value = minimumValue+ (long)(Math.random()*(maxValue-minimumValue))+1;
        return value;
    }

    @Override
    public String formattedRandomValue(long minLength, long maxLength, String formatMask) {
        return randomValue(minLength,maxLength).toString(); //TODO implement when there is requirement for the same
    }

    @Override
    public Long randomValueWithPrefix(long minLength, long maxLength, Long prefix) {
        Long value = randomValue(minLength,maxLength);
        String stringValue = value.toString();

        return super.randomValueWithPrefix(minLength, maxLength, prefix);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
