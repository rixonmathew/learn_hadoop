package com.mockgenerator.strategy.record;

import com.mockgenerator.configuration.Field;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 7/3/13
 * Time: 7:47 PM
 * This class will hold the field value that has been generated
 */
public class FieldValueHolder {

    private Field field;
    private String value;

    public FieldValueHolder(Field field, String value) {
        this.field = field;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
