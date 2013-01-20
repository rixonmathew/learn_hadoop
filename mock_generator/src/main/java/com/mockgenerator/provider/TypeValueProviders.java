package com.mockgenerator.provider;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 11:43 PM
 */
public class TypeValueProviders {

    private final static Logger LOG = Logger.getLogger(TypeValueProviders.class);
    private static Map<String,ValueProvider> valueProviders;

    static {
        valueProviders = new HashMap<String, ValueProvider>();
        valueProviders.put("int",new IDValueProvider());
        valueProviders.put("String",new StringValueProvider());
        valueProviders.put("Date",new DateValueProvider());
        //valueProviders.put("AlphaNumeric",new AlphaNumericValueProvider()); //TODO this needs to be added later on
    }


    public static ValueProvider valueProviderFor(String type){
        if (!valueProviders.containsKey(type)) {
            LOG.debug("Invalid value type specified "+type);
        }
        return valueProviders.get(type);
    }
}
