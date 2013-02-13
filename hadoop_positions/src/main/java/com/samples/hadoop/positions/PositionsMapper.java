package com.samples.hadoop.positions;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.VLongWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 12/2/13
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class PositionsMapper extends Mapper<LongWritable,Text,LongWritable,VLongWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        LongWritable outputKey;
        VLongWritable outputValue;
        String inputRecord = value.toString();
        if (inputRecord.length()<40) {
            System.err.println("Input record is not valid:"+inputRecord);
            return;
        }
        String positionId = inputRecord.substring(0,9);
        String returnValue = inputRecord.substring(30,45);
        if (returnValue.contains("-")) {
            int position = returnValue.indexOf('-');
            returnValue = returnValue.substring(position,returnValue.length());
        }
        outputKey = new LongWritable(Long.valueOf(positionId));
        outputValue = new VLongWritable(Long.valueOf(returnValue));
        context.write(outputKey,outputValue);
    }
}
