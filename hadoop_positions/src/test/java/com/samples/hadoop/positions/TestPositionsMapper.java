package com.samples.hadoop.positions;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.VLongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * This class is used for
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 12/2/13
 * Time: 7:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestPositionsMapper {

    @Test
    public void testMapFunctionality() throws IOException, InterruptedException {
        final String sampleRecord="000010455SHRTFUTCUSqNiBCqIPRCC" +
                "0000000000005390000000000000024085356944000000000000000988392054520130212191411" +
                "00000000000000000000005042013021219125600000000000000000000004290000000000000000000650583" +
                "0000000000000000000018036NAAAAA0000000000000000682403905000000000000000029918290419811129";
        PositionsMapper positionsMapper = new PositionsMapper();
        PositionsMapper.Context context = mock(Mapper.Context.class);
        positionsMapper.map(null,new Text(sampleRecord),context);
        verify(context).write(new LongWritable(10455), new VLongWritable(539));
    }

    @Test
    public void testMapFunctionalityForNegativeReturns() throws IOException, InterruptedException {
        final String sampleRecord="000010204LONGEQUCUSLzjEBchPRCC000000000000-540000000000000081879445745" +
                "0000000000000047967855209201302121912570000000000000000000000837201302121912480000000000000000000000642" +
                "00000000000000000000021660000000000000000000217163NBBBBB0000000000000000024573715000000000000000008922053620090612";
        PositionsMapper positionsMapper = new PositionsMapper();
        PositionsMapper.Context context = mock(Mapper.Context.class);
        positionsMapper.map(null,new Text(sampleRecord),context);
        verify(context).write(new LongWritable(10204), new VLongWritable(-54));
    }
}

