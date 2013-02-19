import com.mockgenerator.configuration.Field;
import com.mockgenerator.configuration.Schema;
import com.mockgenerator.configuration.SchemaParser;
import org.junit.Test;

import java.math.BigDecimal;
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
        final String CONFIGURATION_FILE_NAME = "/home/rixon/workspace/workspace_misc/learn_hadoop/mock_generator/src/test/resources/positions.json";
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
//        for (Field field:fields) {
//            System.out.println("field = " + field);
//        }

    }

    @Test
    public void testConfigurationReaderForFixedWidthSchema() {
        final String CONFIGURATION_FILE_NAME="/home/rixon/workspace/workspace_misc/learn_hadoop/mock_generator/src/test/resources/instruments.json";
        final String expectedName = "instruments";
        final String expectedType = "fixed-width";
        Schema schema = SchemaParser.parse(CONFIGURATION_FILE_NAME);
        assertNotNull(schema);
        assertThat(schema.getName(),is(expectedName));
        assertThat(schema.getType(), is(expectedType));
        List<Field> fields = schema.getFields();
        int expectedFields = 5;
        assertThat(fields.size(),is(expectedFields));
//        for (Field field:fields) {
//            System.out.println("field = " + field);
//        }
    }

    @Test
    public void testConfigurationReaderForFixedWidthPositions() {
        final String CONFIGURATION_FILE_NAME="/home/rixon/workspace/workspace_misc/learn_hadoop/mock_generator/src/test/resources/rp_positions_fw.json";
        final String expectedName = "portfolio_positions";
        final String expectedType = "fixed-width";
        Schema schema = SchemaParser.parse(CONFIGURATION_FILE_NAME);
        assertNotNull(schema);
        assertThat(schema.getName(),is(expectedName));
        assertThat(schema.getType(), is(expectedType));
        List<Field> fields = schema.getFields();
        int expectedFields = 19;
        assertThat(fields.size(),is(expectedFields));
//        for (Field field:fields) {
//            System.out.println("field = " + field);
//        }
    }
}
