package com.mockgenerator.provider;

import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 22/1/13
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class AbstractValueProvider<TYPE> implements ValueProvider<TYPE> {

    protected final Random random = new Random();

    @Override
    public TYPE randomValueFromRange(List<TYPE> values) {
        int index;
        TYPE randomValue=null;
        if (values!=null && !values.isEmpty()){
            int size = values.size();
            randomValue = values.get(random.nextInt(size));
        }
        return randomValue;
    }
}
