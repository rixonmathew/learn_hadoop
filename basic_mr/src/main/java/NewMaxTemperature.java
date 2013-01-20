import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

/**
 * User: rixonmathew
 * Date: 11/01/13
 * Time: 7:02 PM
 * Trying out the MapReduce logic using new classes
 */
public class NewMaxTemperature extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        if (args.length!=2){
            System.err.printf("Usage: %s <input path> <output path>", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }

        Job job = new Job();
        job.setJarByClass(NewMaxTemperature.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(NewMaxTemperatureMapper.class);
        job.setCombinerClass(NewMaxTemperatureReducer.class);
        job.setReducerClass(NewMaxTemperatureReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        return job.waitForCompletion(true)?1:0;
    }

    static class NewMaxTemperatureMapper extends Mapper<LongWritable,Text,Text,IntWritable> {

        private static final int MISSING = 9999;
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

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
                context.write(new Text(year), new IntWritable(airTemprature));
            }
        }
    }

    static class NewMaxTemperatureReducer extends Reducer<Text,IntWritable,Text,IntWritable> {

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int maxValue = Integer.MIN_VALUE;
            for (IntWritable value:values) {
                maxValue = Math.max(value.get(),maxValue);
            }
            context.write(key,new IntWritable(maxValue));
        }
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new NewMaxTemperature(),args);
        System.exit(exitCode);
    }
}
