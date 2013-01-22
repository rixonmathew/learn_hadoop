package com.mockgenerator.provider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertTrue;

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
        for (int i=0;i<100;i++) {
            String randomValue = stringValueProvider.randomValue(minLength, maxLength);
            System.out.println("stringValueProvider = " + randomValue);
            assertThat(randomValue.length(), is(greaterThanOrEqualTo(minLength)));
            assertThat(randomValue.length(),is(lessThanOrEqualTo(maxLength)));
        }
    }

    @Test
    public void testRandomValueWithinRange() {
        List<String> randomValues = Arrays.asList("hello","world","meter","counter","laughter");
        for (int i=0;i<100;i++){
            String value = stringValueProvider.randomValueFromRange(randomValues);
            assertTrue(randomValues.contains(value));
            System.out.println("value = " + value);
        }
    }
}
