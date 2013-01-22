package com.mockgenerator.configuration;

/**
* User: rixonmathew
* Date: 19/01/13
* Time: 2:30 PM
* This class represents a field in the file
*/
public class Field {
    private String name;
    private String type;
    private int minLength;
    private int maxLength;
    private String defaultValue;
    private String formatMask;
    private String range;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFormatMask() {
        return formatMask;
    }

    public void setFormatMask(String formatMask) {
        this.formatMask = formatMask;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", minLength=" + minLength +
                ", maxLength=" + maxLength +
                ", defaultValue='" + defaultValue + '\'' +
                ", formatMask='" + formatMask + '\'' +
                ", range='" + range + '\'' +
                '}';
    }


}
