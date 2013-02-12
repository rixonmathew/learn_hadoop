package com.mockgenerator.provider;

import com.mockgenerator.configuration.Field;
import com.mockgenerator.util.DateUtil;

import java.util.Date;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 9:43 PM
 */
public class DateValueProvider extends AbstractValueProvider<Date> {
    @Override
    public Date randomValue() {
        return FileBasedRandomValueProvider.randomDate();
    }

    @Override
    Date formatValueFromString(String stringValue) {
        return DateUtil.getFormattedDate(stringValue);
    }

    @Override
    public Date randomValue(long minLength, long maxLength) {
        return randomValue();
    }

    @Override
    public String randomValueAsString(Field<Date> field) {
        Date randomValue = super.randomValue(field);
        return DateUtil.getFormattedDate(randomValue,field.getFormatMask());
    }
}
