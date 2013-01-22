import com.mockgenerator.configuration.Field;
import com.mockgenerator.configuration.Schema;
import com.mockgenerator.configuration.SchemaParser;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * User: rixonmathew
 * Date: 19/01/13
 * Time: 1:24 PM
 * This class will test the configuration reading functionality
 */
public class TestJSONConfigReader {

    @Test
    public void testValidConfigurationReader() {
        final String CONFIGURATION_FILE_NAME = "positions.json";
        final String expectedName = "position";
        final String expectedType = "delimited";
        final String expectedSeparator = ",";
        Schema schema = SchemaParser.parse(CONFIGURATION_FILE_NAME);
        assertNotNull(schema);
        assertThat(schema.getName(), is(expectedName));
        assertThat(schema.getType(), is(expectedType));
        assertThat(schema.getSeparator(),is(expectedSeparator));
        List<Field> fields = schema.getFields();
        int expectedFields = 5;
        assertThat(fields.size(),is(expectedFields));
        for (Field field:fields) {
            System.out.println("field = " + field);
        }

    }

}
