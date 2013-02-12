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
    public Date randomValue(long minLength, long maxLength) {
        return randomValue();
    }

    @Override
    public String randomValueAsString(Field<Date> field) {
        Date randomValue = randomValue();
        String formatMask = field.getFormatMask()!=null?field.getFormatMask():"YmdHMS:L:N";
        return DateUtil.getFormattedDate(randomValue,formatMask);
    }

    @Override
    protected Date valueFromString(String value) {
        return DateUtil.getFormattedDate(value);
    }
}
