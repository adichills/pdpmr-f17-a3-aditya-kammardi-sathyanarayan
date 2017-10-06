package com.pdpmrd.a3;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.io.LongWritable;


import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.RecordReader;

/*
    This class is taken from http://analyticspro.org/2012/08/01/wordcount-with-custom-record-reader-of-textinputformat/
    I was trying out few things to implement overlapping of lines ,but ended up with an infinite loop ,hence not using this
    class now
 */

public class NLinesInputFormat extends FileInputFormat {



    @Override
    public RecordReader<LongWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context) {
        return new NLinesRecordReader();
    }
}
