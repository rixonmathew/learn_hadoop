package com.mockgenerator.provider;

import com.mockgenerator.configuration.Field;
import com.mockgenerator.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 12/2/13
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimestampValueProvider extends AbstractValueProvider<Date> {

    @Override
    Date randomValue(long minLength, long maxLength) {
        return randomValue();
    }

    @Override
    public Date randomValue() {
        long newTime = System.currentTimeMillis()-random.nextInt(100000);
        return new Date(newTime);
    }

    @Override
    public String randomValueAsString(Field<Date> field) {
        Date randomValue = super.randomValue(field);
        return DateUtil.getFormattedDate(randomValue, field.getFormatMask());
    }
}
