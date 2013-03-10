package com.mockgenerator.strategy.record;

import com.mockgenerator.configuration.Field;
import com.mockgenerator.configuration.Schema;
import com.mockgenerator.generator.Options;
import com.mockgenerator.provider.TypeValueProviders;
import com.mockgenerator.provider.ValueProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 25/1/13
 * Time: 10:53 AM
 * This strategy is responsible for creating fixedWidthRecord based on the fields
 */
public class FixedWidthRecordCreationStrategy extends AbstractRecordCreationStrategy {

    @Override
    public String createRecordWithOverrides(Schema schema, Options options, long recordCounter, Map<Field, String> overriddenFields) {
        StringBuilder record = new StringBuilder();
        List<Field> fields = schema.getFields();
        for (Field field : fields) {
            String value = determineFieldValue(field,overriddenFields);
            record.append(value);
        }
        return record.toString();
    }
}
