package com.pdpmrd.a3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/*
    Author : Aditya Kammardi Sathyanarayan
 */

public class LetterCountMapper
        extends Mapper<Object, Text, Text, LongWritable> {

    private final static LongWritable one = new LongWritable(1);
    private Text letter = new Text();

    public void map(Object key, Text value, Context context
    ) throws IOException, InterruptedException {
        String valueString = value.toString();
        // using a  regex to keep only alpha numeric characters in the inputline
        valueString = valueString.replaceAll("[^\\p{Alpha}]","").toLowerCase();
        for (Character c: valueString.toCharArray()){
            letter.set(c.toString());
            context.write(letter,one);
        }
    }
}
