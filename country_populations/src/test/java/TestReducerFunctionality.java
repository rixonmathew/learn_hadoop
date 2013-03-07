import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.VLongWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 25/2/13
 * Time: 7:48 PM
 * This is a test file for testing the reducer of the populations
 */
public class TestReducerFunctionality {

    @Test
    public void testReducerFunctionality() throws IOException, InterruptedException {
        //TODO add functionality for reducer
        PopulationsReducer populationsReducer = new PopulationsReducer();
        String expectedCountry = "IN";
        int expectedYear = 1971;
        float expectedDrop = 15.10f;
        List<Text> yearAndTemperatures = createMockListOfTemperatures();
        Reducer.Context context = mock(Reducer.Context.class);
        populationsReducer.reduce(new Text(expectedCountry),yearAndTemperatures,context);
        verify(context).write(new Text(expectedCountry),new Text(expectedYear+":"+expectedDrop));
    }

    private List<Text> createMockListOfTemperatures() {
        List<Text> mockList = new ArrayList<Text>();
        mockList.add(new Text("1969:100000"));
        mockList.add(new Text("1970:150000"));
        mockList.add(new Text("1971:140000"));
        mockList.add(new Text("1972:170000"));
        mockList.add(new Text("1973:165000"));
        mockList.add(new Text("1975:168000"));
        return mockList;
    }
}
