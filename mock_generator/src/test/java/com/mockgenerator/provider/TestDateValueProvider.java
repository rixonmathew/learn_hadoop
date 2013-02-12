package com.mockgenerator.provider;

import com.mockgenerator.configuration.Field;
import com.mockgenerator.util.DateUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 22/1/13
 * Time: 12:04 PM
 * This class is used for testing the Date value provider
 */
public class TestDateValueProvider {

    private DateValueProvider dateValueProvider;

    @Before
    public void setUp() throws Exception {
        dateValueProvider = new DateValueProvider();
    }

    @After
    public void tearDown() {
        dateValueProvider = null;
    }

    @Test
    public void testDateValueProvider() {
        for (int i=0;i<1000;i++) {
            System.out.println("dateValueProvider = " + DateUtil.getDateAsString(dateValueProvider.randomValue()));
        }
    }

    @Test
    public void testDateValueProviderWithRange() {
        List<Date> values = Arrays.asList(DateUtil.getFormattedDate("01/01/2001"),
                DateUtil.getFormattedDate("01/03/2001"),DateUtil.getFormattedDate("05/05/2007"));
        for (int i=0;i<100;i++) {
            Date value = dateValueProvider.randomValueFromRange(values);
            assertTrue(values.contains(value));
            //System.out.println("value = " + DateUtil.getDateAsString(value));
        }

    }
//    @Test
//    public void testDateValueProviderAsString() {
//        Field<Date> field = mockField();
//        for (int i=0;i<1000;i++) {
//            System.out.println("date = " + dateValueProvider.randomValueAsString(field));
//        }
//    }

    @Test
    public void testDateFormatCheck(){
        for (int i=0;i<1000;i++){
            Date date = dateValueProvider.randomValue();
            //System.out.printf("%s formatted(YYYYMMDD): %s \n",date.toString(),DateUtil.getFormattedDate(date,"dd-MM-YYYY"));
        }
    }

    private Field<Date> mockField() {
        Field<Date> dateField = new Field<Date>();
        dateField.setType("date");
        dateField.setFormatMask("YYYYMMDD");
        dateField.setFixedLength(8);
        return dateField;
    }
}