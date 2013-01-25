package com.mockgenerator.strategy.record;

import com.mockgenerator.configuration.Field;
import com.mockgenerator.configuration.Schema;
import com.mockgenerator.generator.Options;
import com.mockgenerator.provider.TypeValueProviders;
import com.mockgenerator.provider.ValueProvider;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 25/1/13
 * Time: 10:39 AM
 * This strategy creates records when the record is delimited
 */
public class DelimitedRecordCreationStrategy implements RecordCreationStrategy {

    @Override
    public String createRecord(Schema schema, Options options, long recordCounter) {
        StringBuilder record = new StringBuilder();
        List<Field> fields = schema.getFields();
        for (int j = 0; j < fields.size(); j++) {
            Field field = fields.get(j);
            ValueProvider valueProvider = TypeValueProviders.valueProviderFor(field.getType());
            Object value = valueProvider.randomValue(field);
            record.append(value);
            if (j != fields.size() - 1) {
                record.append(schema.getSeparator());
            }
        }
        return record.toString();
    }
}
