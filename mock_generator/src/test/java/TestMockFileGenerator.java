import com.mockgenerator.configuration.Schema;
import com.mockgenerator.configuration.SchemaParser;
import com.mockgenerator.generator.FileGenerator;
import com.mockgenerator.generator.Options;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 12:31 PM
 */
public class TestMockFileGenerator {

    @Test
    public void testMockDelimitedFileGeneration() {
        String delimitedSchemaName = "/home/rixon/workspace/workspace_misc/learn_hadoop/mock_generator/src/test/resources/positions.json";
        validateMockFileCreation(delimitedSchemaName);
    }

    @Test
    public void testMockFixedWidthFileGeneration() throws IOException {
       String fixedWidthSchemaName="/home/rixon/workspace/workspace_misc/learn_hadoop/mock_generator/src/test/resources/instruments.json";
        validateFixedWidthMockFileCreation(fixedWidthSchemaName);
    }

    @Test
    public void testMockPositionsFixedWidthFileGeneration() throws IOException{
        String fixedWidthSchemaName="/home/rixon/workspace/workspace_misc/learn_hadoop/mock_generator/src/test/resources/rp_positions_fw.json";
        validatePositionsFixedWidthMockFileCreation(fixedWidthSchemaName);

    }


    private void validatePositionsFixedWidthMockFileCreation(String schemaName) throws IOException {
        int expectedMockLength = 287;
        Schema schema = SchemaParser.parse(schemaName);
        final String outputDirectory = schemaName.substring(0,schemaName.indexOf("."));
        Options options = createMockOptions(outputDirectory, "random");
        FileGenerator fileGenerator = new FileGenerator(options,schema);
        assertFiles(options, fileGenerator);
        assertRecordLength(options,expectedMockLength);
    }


    private void validateFixedWidthMockFileCreation(String schemaName) throws IOException {
        int expectedMockLength = 148;
        Schema schema = SchemaParser.parse(schemaName);
        final String outputDirectory = schemaName.substring(0,schemaName.indexOf("."));
        Options options = createMockOptions(outputDirectory, "random");
        FileGenerator fileGenerator = new FileGenerator(options,schema);
        assertFiles(options, fileGenerator);
        assertRecordLength(options,expectedMockLength);
    }



    private void validateMockFileCreation(String schemaName) {
        Schema schema = SchemaParser.parse(schemaName);
        final String outputDirectory = schemaName.substring(0,schemaName.indexOf("."));
        Options options = createMockOptions(outputDirectory, "random");
        FileGenerator fileGenerator = new FileGenerator(options,schema);
        assertFiles(options, fileGenerator);
    }


    private void assertFiles(Options options, FileGenerator fileGenerator) {
        fileGenerator.generateFiles();
        File file = new File(options.getOutputDirectory());
        assertThat(file.isDirectory(),is(true));
        File[] files = file.listFiles();
        assertNotNull(files);
        assertThat((long) files.length,is(options.getNumberOfFileSplits()));
//        for (File file1:files) {
//            System.out.println("file1 = " + file1.getName());
//        }
    }


    private void assertRecordLength(Options options,int expectedMockLength) throws IOException {
        File file = new File(options.getOutputDirectory());
        assertThat(file.isDirectory(),is(true));
        File[] files = file.listFiles();
        assertNotNull(files);
        for(File file1:files) {
            BufferedReader reader = new BufferedReader(new FileReader(file1));
            String record;
            while ((record=reader.readLine())!=null){
                assertThat(expectedMockLength,is(record.length()));
            }
        }
    }

    private Options createMockOptions(final String outputDirectory, String generationType) {
        Options options = new Options();
        options.setGenerationType(generationType);
        options.setNumberOfFileSplits(3);
        options.setNumberOfRecordsPerSplit(5000);
        options.setOutputDirectory(outputDirectory);
        return options;
    }
}

