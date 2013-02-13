package com.samples.hadoop.positions;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.VLongWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 12/2/13
 * Time: 7:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestPositionsReducer {

    @Test
    public void testReduceFunctionality() throws IOException, InterruptedException {
        PositionsReducer positionsReducer = new PositionsReducer();
        long expectedPosition = 100100l;
        LongWritable positionId = new LongWritable(expectedPosition);
        List<VLongWritable> returns = Arrays.asList(new VLongWritable(500l),new VLongWritable(600l),new VLongWritable(-100l),new VLongWritable(90l));
        Reducer.Context context = mock(Reducer.Context.class);
        positionsReducer.reduce(positionId,returns,context);
        double expectedAverageReturn = 272.5;
        verify(context).write(new LongWritable(expectedPosition),new DoubleWritable(expectedAverageReturn));
    }

}