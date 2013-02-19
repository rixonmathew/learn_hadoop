import com.mockgenerator.MockGenerationClient;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.fail;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 6/2/13
 * Time: 10:41 AM
 * This class is the test file for testing Client
 */
public class ClientTest {

    @Test
    public void testDriver() {
        String[] args = mockArguments("/home/rixon/workspace/workspace_misc/learn_hadoop/mock_generator/src/test/resources/mockProperties.properties");
        executeMain(args);
    }

    @Test
    public void testDriverWithMinOptions() {
        String[] args = mockArguments("/home/rixon/workspace/workspace_misc/learn_hadoop/mock_generator/src/test/resources/minProperties.properties");
        executeMain(args);
    }

    private String[] mockArguments(String propertyFileName) {
        String[] args = new String[2];
        args[0] = "--file";
        args[1] = propertyFileName;
        return args;
    }

    private void executeMain(String[] args) {
        try {
            MockGenerationClient.main(args);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            fail("an exception occurred executing the driver");
        }
    }

}
