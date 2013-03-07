import com.mockgenerator.configuration.Schema;
import com.mockgenerator.configuration.SchemaParser;
import com.mockgenerator.generator.FileGenerator;
import com.mockgenerator.generator.Options;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 7/3/13
 * Time: 6:02 PM
 * This class is used for testing the functionality of Custom file based generation strategy.
 * The usecase here is the quotes data where volume is required but data needs to follow a structure
 * so that eventual analytics that gets done on the result is meaningful
 */
public class TestCustomFileGenerationStrategy {

    @Test
    public void testQuoteDataGenerationStrategy() {
        String quotesSchemaName = "/home/rixon/workspace/workspace_misc/learn_hadoop/mock_generator/src/test/resources/quotes.json";
        Schema schema = SchemaParser.parse(quotesSchemaName);
        String outputDirectory = "mock_"+quotesSchemaName.substring(0,quotesSchemaName.indexOf("."));
        Options options = createMockOptions(outputDirectory,"class","com.mockgenerator.strategy.QuoteDataGenerationStrategy");
        FileGenerator fileGenerator = new FileGenerator(options,schema);
        assertFiles(options,fileGenerator);

    }

    private Options createMockOptions(final String outputDirectory, String generationType,String generationClass) {
        Options options = new Options();
        options.setGenerationType(generationType);
        options.setGenerationClass(generationClass);
        options.setNumberOfFileSplits(3);
        options.setNumberOfRecordsPerSplit(5000);
        options.setOutputDirectory(outputDirectory);
        return options;
    }
    private void assertFiles(Options options, FileGenerator fileGenerator) {
        fileGenerator.generateFiles();
        File file = new File(options.getOutputDirectory());
//        assertThat(file.isDirectory(), is(true));
//        File[] files = file.listFiles();
//        assertNotNull(files);
//        assertThat((long) files.length, is(options.getNumberOfFileSplits()));
//        for (File file1:files) {
//            System.out.println("file1 = " + file1.getName());
//        }
    }

}
