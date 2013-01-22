package com.mockgenerator.provider;

import java.util.Random;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 11:21 PM
 */
public class StringValueProvider extends AbstractValueProvider<String> {

    @Override
    public String randomValue() {
        return randomStringValue(random.nextInt(300));
    }

    @Override
    public String randomValue(long minLength, long maxLength) {
        StringBuffer placeHolder = new StringBuffer();
        int length = (int)(minLength+(int)(Math.random()*(maxLength-minLength)+1));
        placeHolder.append(randomStringValue(length));
        return placeHolder.toString();
    }

    @Override
    public String formattedRandomValue(long minLength, long maxLength, String formatMask) {
        return randomValue(minLength,maxLength);//TODO implement when there is a requirement
    }

    private String randomStringValue(int length) {
        char[] chars = new char[length];
        for  (int i=0;i<length;i++){
            int ASCII_A = 65;
            chars[i] = (char)(ASCII_A +random.nextInt(26));
            if (random.nextInt(2)==1) {
                chars[i] = Character.toLowerCase(chars[i]);
            }
        }
        return new String(chars);
    }
}
