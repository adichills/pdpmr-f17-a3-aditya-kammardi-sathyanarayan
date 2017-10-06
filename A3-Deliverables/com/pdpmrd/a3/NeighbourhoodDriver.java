package com.pdpmrd.a3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
    Author : Aditya Kammardi Sathyanarayan
 */

public class NeighbourhoodDriver {
    public static Boolean drive(String[] args) throws Exception {


        Integer iteration = Integer.parseInt(args[1]);
        String inputDirectoryPath = args[2];
        String letterCountFilePath = args[3] + args[1] + "/part-r-00000";

        String outputDirectoryPath = args[4];

        Configuration conf = new Configuration();
        conf.set("K",args[0]);

        String letterCountScores = A3Util.letterCountString(letterCountFilePath);
        long [] letterScores = A3Util.computeLetterScores(letterCountScores);
        conf.set("letterScores",A3Util.arrayToString(letterScores));



        Job job = Job.getInstance(conf, "Neighborhood Score");
        job.setJarByClass(NeighbourhoodDriver.class);

        job.setMapperClass(NeighborhoodMapper.class);
        job.setReducerClass(NeighborhoodReducer.class);


        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);



        FileInputFormat.addInputPath(job, new Path(inputDirectoryPath));
        FileOutputFormat.setOutputPath(job, new Path(outputDirectoryPath+iteration));
        return (job.waitForCompletion(true));
    }
}
