package com.mockgenerator.provider;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 22/1/13
 * Time: 2:10 PM
 * This class represents the ValueProvider for AlphaNumeric Type of data
 */
public class AlphaNumericValueProvider extends AbstractValueProvider<String> {

    @Override
    public String randomValue() {
        return randomAlphaNumericValue(300);
    }

    @Override
    String formatValueFromString(String stringValue) {
        return stringValue;
    }

    @Override
    public String randomValue(long minLength, long maxLength) {
        int length = (int)minLength +(int)(Math.random()*(maxLength-minLength));
        return randomAlphaNumericValue(length);
    }

    @Override
    public String formattedRandomValue(long minLength, long maxLength, String formatMask) {
        return randomValue(minLength,maxLength); //TODO check on how format mask can be used for alpha numeric
    }

    private String randomAlphaNumericValue(int length) {
        char[] chars = new char[length];
        for  (int i=0;i<length;i++){
            int ASCII_A = 65;
            if(random.nextInt(2)==0){
                chars[i] = (char)(ASCII_A +random.nextInt(26));
            } else {
                chars[i] = Character.forDigit(random.nextInt(10),10);
            }
        }
        return new String(chars);
    }


}
