import com.mockgenerator.MockGenerationClient;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static junit.framework.TestCase.fail;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 6/2/13
 * Time: 10:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClientTest {

    @Test
    public void testDriver() {
        String[] args = new String[2];
        args[0] = "--file";
        args[1] = "/home/rixon/workspace/workspace_misc/learn_hadoop/mock_generator/src/test/resources/mockProperties.properties";

        try {
            MockGenerationClient.main(args);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            fail("an exception occurred while reading properties file");
        }
    }
}
