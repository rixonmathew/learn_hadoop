package com.mockgenerator.provider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 10:14 PM
 */
public class TestIDValueProvider {

    IDValueProvider valueProvider;

    @Before
    public void setup() {
       valueProvider = new IDValueProvider();
    }

    @After
    public void tearDown() {
        valueProvider = null;
    }

    @Test
    public void testRandomValueProviderWithNoLimits() {
        int tries = 100;
        for (int i=0;i<tries;i++){
            System.out.println("valueProvider = " + valueProvider.randomValue());
        }
    }

    @Test
    public void testRandomValueProviderWithLimits() {
        int tries = 2000;
        for (int i=0;i<tries;i++) {
            System.out.println("valueProvider = " + valueProvider.randomValue(2,15));
        }

    }
}
