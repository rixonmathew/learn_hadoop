package com.mockgenerator.provider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 11:19 PM
 */
public class TestStringValueProvider {

    StringValueProvider stringValueProvider;

    @Before
    public void setup(){
        stringValueProvider = new StringValueProvider();
    }

    @After
    public void tearDown(){
        stringValueProvider = null;
    }

    @Test
    public void testRandomValueWithNoLimits(){
        for (int i=0;i<100;i++) {
            System.out.println("stringValueProvider = " + stringValueProvider.randomValue());
        }
    }

    @Test
    public void testRandomValueWithinLimits() {
        int minLength = 2;
        int maxLength = 25;
        String constantPart = "ADANTE";
        for (int i=0;i<100;i++) {
            System.out.println("stringValueProvider = " + stringValueProvider.randomValue(minLength,maxLength));
        }
    }
}
