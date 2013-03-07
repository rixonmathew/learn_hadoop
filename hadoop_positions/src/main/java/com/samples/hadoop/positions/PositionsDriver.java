package com.samples.hadoop.positions;

import com.mongodb.hadoop.MongoOutputFormat;
import com.mongodb.hadoop.util.MongoConfigUtil;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.VLongWritable;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;




/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 12/2/13
 * Time: 7:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class PositionsDriver extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        if (args.length!=2){
            System.err.printf("Usage: %s <input path> <output path>", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }


        Job job = new Job();
        job.setJarByClass(PositionsDriver.class);

        //MongoConfigUtil.setOutputURI(job.getConfiguration(),"mongodb://localhost/test.users");
        FileInputFormat.addInputPath(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        job.setMapperClass(PositionsMapper.class);
        job.setReducerClass(PositionsReducer.class);

        job.getConfiguration().setBoolean("mapred.output.compress", true);
        job.getConfiguration().setClass("mapred.output.compression.codec", GzipCodec.class,
                CompressionCodec.class);
        job.getConfiguration().setStrings("mapred.textoutputformat.separator","==>");
        job.setMapOutputValueClass(VLongWritable.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(DoubleWritable.class);
        //job.setOutputFormatClass(MongoOutputFormat.class);

        return job.waitForCompletion(true)?1:0;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new PositionsDriver(),args);
        System.exit(exitCode);
    }
}
