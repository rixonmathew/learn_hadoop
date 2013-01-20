import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;


/**
 * User: rixonmathew
 * Date: 13/01/13
 * Time: 8:21 PM
 * This is the test class to test the functionality of the mapper class
 */
public class MaxTemperatureMapperTest {

    @Test
    public void processValidRecords() throws IOException, InterruptedException {
        NewMaxTemperature.NewMaxTemperatureMapper mapper = new NewMaxTemperature.NewMaxTemperatureMapper();
        Text value = new Text("0043011990999991950051518004+68750+023550FM-12+0382" + // Year ^^^^
                "99999V0203201N00261220001CN9999999N9-00111+99999999999"); // Temperature ^^^^^
        NewMaxTemperature.NewMaxTemperatureMapper.Context context = mock(Mapper.Context.class);
        mapper.map(null,value,context);
        verify(context).write(new Text("1950"),new IntWritable(-11));
    }

    @Test
    public void ignoreInvalidTemperatureRecords() throws IOException, InterruptedException {
        NewMaxTemperature.NewMaxTemperatureMapper mapper = new NewMaxTemperature.NewMaxTemperatureMapper();
        Text value = new Text("0043011990999991950051518004+68750+023550FM-12+0382" + // Year ^^^^
                "99999V0203201N00261220001CN9999999N9+99991+99999999999"); // Temperature ^^^^^
        NewMaxTemperature.NewMaxTemperatureMapper.Context context = mock(Mapper.Context.class);
        mapper.map(null,value,context);
        verify(context,never()).write(any(Text.class),any(IntWritable.class));
    }


    @Test
    public void testReduceFunctionality() throws IOException, InterruptedException {
        NewMaxTemperature.NewMaxTemperatureReducer reducer = new NewMaxTemperature.NewMaxTemperatureReducer();
        Text key = new Text("1901");
        List<IntWritable> values = Arrays.asList(new IntWritable(10),new IntWritable(5));
        NewMaxTemperature.NewMaxTemperatureReducer.Context context = mock(NewMaxTemperature.NewMaxTemperatureReducer.Context.class);
        reducer.reduce(key,values,context);
        verify(context).write(key,new IntWritable(10));
    }

}
