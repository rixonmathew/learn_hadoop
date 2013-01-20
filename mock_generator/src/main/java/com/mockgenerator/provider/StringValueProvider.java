package com.mockgenerator.provider;

import java.util.Random;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 11:21 PM
 */
public class StringValueProvider implements ValueProvider<String> {

    private Random random = new Random();

    @Override
    public String randomValue() {
        return randomStringValue(random.nextInt(300));
    }

    @Override
    public String randomValue(long minLength, long maxLength) {
        StringBuffer placeHolder = new StringBuffer();
        placeHolder.append(randomStringValue((int)maxLength));
        return placeHolder.toString();
    }

    private String randomStringValue(int length) {
        char[] chars = new char[length];
        for  (int i=0;i<length;i++){
            chars[i] = (char)(65+random.nextInt(26));
        }
        return new String(chars);
    }
}
