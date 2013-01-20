import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;

/**
 * User: rixonmathew
 * Date: 06/01/13
 * Time: 8:59 PM
 *
 * This class acts as the mapper for MR job to find the unique temprature for each year
 */

public class MaxTemperatureMapper extends MapReduceBase implements Mapper<LongWritable,Text,Text,IntWritable> {

    private final static int MISSING = 9999;
    @Override
    public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

        String line = value.toString();
        String year = line.substring(15,19);
        int airTemprature;
        if (line.charAt(87)=='+'){
            airTemprature = Integer.parseInt(line.substring(88,92));
        } else {
            airTemprature = Integer.parseInt(line.substring(87,92));
        }

        String quality = line.substring(92,93);
        if ((airTemprature!=MISSING) && (quality.matches("[01459]"))) {
            output.collect(new Text(year),new IntWritable(airTemprature));
        }
    }
}