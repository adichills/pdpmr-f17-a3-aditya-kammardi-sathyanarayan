package com.pdpmrd.a3;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
    Author : Aditya Kammardi Sathyanarayan
 */

public class NeighborhoodReducer
        extends Reducer<Text, IntWritable, Text, DoubleWritable> {
    private DoubleWritable result = new DoubleWritable();

    public void reduce(Text key, Iterable<IntWritable> values,
                       Context context
    ) throws IOException, InterruptedException {

        // Logic to calculate the median
        // Sort the values list and pick the middle element if the length of the list is odd else average of two middle elements
        // this is brute force approach ,and one of the reasons the reduce step is taking a while to compute ,need to improve the
        // logic.
        double median = 0;
        ArrayList<Integer> valueList = new ArrayList<Integer>();
        for (IntWritable val : values) {
            valueList.add(val.get());
        }
        Collections.sort(valueList);
        int mid = valueList.size()/2;
        if (valueList.size() % 2 == 0 && valueList.size()>2){
            ;
            median = (valueList.get(mid) + valueList.get(mid+1)) * 0.5;
        }
        else{
            median = valueList.get(mid);
        }

        result.set(median);
        context.write(key, result);
    }
}
