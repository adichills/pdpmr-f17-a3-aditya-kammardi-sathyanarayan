package com.pdpmrd.a3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
    Author : Aditya Kammardi Sathyanarayan
 */


public class LetterCountDriver {

    public static Boolean drive(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Letter count");
        job.setJarByClass(LetterCountDriver.class);

        job.setMapperClass(LetterCountMapper.class);
        job.setCombinerClass(LetterCountReducer.class);
        job.setReducerClass(LetterCountReducer.class);

        //So that we get only one output file
        job.setNumReduceTasks(1);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        Integer iteration = Integer.parseInt(args[1]);


        FileInputFormat.addInputPath(job, new Path(args[2]));
        FileOutputFormat.setOutputPath(job, new Path(args[3]+iteration));
        return (job.waitForCompletion(true));
    }
}
