package com.mockgenerator.provider;

import java.util.Date;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 9:43 PM
 */
public class DateValueProvider implements ValueProvider<Date> {
    @Override
    public Date randomValue() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date randomValue(long minLength, long maxLength) {
        return randomValue();
    }
}
