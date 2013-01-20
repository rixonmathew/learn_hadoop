import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.Iterator;

/**
 * User: rixonmathew
 * Date: 07/01/13
 * Time: 7:26 AM
 * This class is the reducer for finding maximum temperature
 */
public class MaxTemperatureReducer extends MapReduceBase implements Reducer<Text,IntWritable,Text,IntWritable> {
    @Override
    public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

        int maxValue = Integer.MAX_VALUE;
        while (values.hasNext()){
            maxValue = Math.max(maxValue,values.next().get());
        }
        output.collect(key,new IntWritable(maxValue));
    }
}
