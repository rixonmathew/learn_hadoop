package com.mockgenerator.configuration;

import java.util.List;

/**
* User: rixonmathew
* Date: 20/01/13
* Time: 12:17 PM
* This class represents the schema
*/
public class Schema {
    private String name;
    private String type;
    private String separator;
    List<Field> fields;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSeparator() {
        return separator;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
