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
 * Time: 10:53 AM
 * This strategy is responsible for creating fixedWidthRecord based on the fields
 */
public class FixedWidthRecordCreationStrategy implements RecordCreationStrategy {
    @Override
    public String createRecord(Schema schema, Options options, long recordCounter) {
        StringBuilder record = new StringBuilder();
        List<Field> fields = schema.getFields();
        for (Field field : fields) {
            ValueProvider valueProvider = TypeValueProviders.valueProviderFor(field.getType());
            if (valueProvider == null) {
                System.out.println("VP null for field" + field);
            }
            String value = valueProvider.randomValueAsString(field);
            record.append(value);
        }
        return record.toString();
    }
}
