package com.mockgenerator.strategy.record;

import com.mockgenerator.configuration.Field;
import com.mockgenerator.configuration.Schema;
import com.mockgenerator.generator.Options;
import com.mockgenerator.provider.TypeValueProviders;
import com.mockgenerator.provider.ValueProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 25/1/13
 * Time: 10:39 AM
 * This strategy creates records when the record is delimited
 */
public class DelimitedRecordCreationStrategy extends AbstractRecordCreationStrategy {

    @Override
    public String createRecordWithOverrides(Schema schema, Options options, long recordCounter, Map<Field, String> overriddenFields) {
        StringBuilder record = new StringBuilder();
        List<Field> fields = schema.getFields();
        for (int j = 0; j < fields.size(); j++) {
            Field field = fields.get(j);
            String value = determineFieldValue(field,overriddenFields);
            record.append(value);
            if (j != fields.size() - 1) {
                record.append(schema.getSeparator());
            }
        }
        return record.toString();
    }
}
