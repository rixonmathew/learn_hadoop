package com.mockgenerator.provider;

import com.mockgenerator.configuration.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 12/2/13
 * Time: 10:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestNumberValueProvider {

    private ValueProvider<BigDecimal> valueProvider;

    @Before
    public void setup(){
        valueProvider = TypeValueProviders.valueProviderFor("BigDecimal");
    }

    @After
    public void teardown() {
        valueProvider = null;
    }

    @Test
    public void testBasicValueProvider() {
        Field<BigDecimal> numberField = mockField();
        for (int i=0;i<1000;i++) {
            BigDecimal randomValue = valueProvider.randomValue(numberField);
            int compareToMin = randomValue.compareTo(numberField.getMinValue());
            int compareToMax = randomValue.compareTo(numberField.getMaxValue());
            assertThat(Integer.valueOf(compareToMin),is(1));
            assertThat(Integer.valueOf(compareToMax),is(-1));
            //System.out.println("randomValue = " + randomValue);
        }
    }

    @Test
    public void testStringValueFromProvider() {
        Field<BigDecimal> numberField = mockField();
        for (int i=0;i<1000;i++) {
            String stringValue = valueProvider.randomValueAsString(numberField);
            //System.out.println("stringValue = " + stringValue);
        }
    }

    private Field<BigDecimal> mockField() {
        Field<BigDecimal> numberField = new Field<BigDecimal>();
        numberField.setFixedLength(25);
        numberField.setMinValue(BigDecimal.valueOf(1000));
        numberField.setMaxValue(BigDecimal.valueOf(10000000000000000L));
        numberField.setPadding("0");
        return numberField;
    }
}
