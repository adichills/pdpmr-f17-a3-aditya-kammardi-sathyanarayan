package com.pdpmrd.a3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/*
    Author : Aditya Kammardi Sathyanarayan
 */

public class NeighborhoodMapper extends Mapper<Object, Text, Text, IntWritable> {

    // utility function to compute the word's score
    private static int wordScore(String word,long [] scoreOfLetters){
        int score = 0;
        char[] arr = word.toCharArray();
        for (char c : arr){
            score += scoreOfLetters[(int)c - (int)'a'];
        }
        return score;
    }

    Text word = new Text();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {


        Configuration conf = context.getConfiguration();
        Integer k = conf.getInt("K",2);

        //fetch the letterScores stored as a string from the conf
        String scoreLettersString = conf.get("letterScores");
        //convert the string scores into a long array of 26 characters ,one cell for each alphabet
        long [] scoreOfLetters = A3Util.stringToArray(scoreLettersString);

        String lines = value.toString();
        //regex to filter out all characters except ,spaces and alphabets
        lines = lines.replaceAll("[^a-zA-Z\\s]","").toLowerCase().trim();
        // split the words by spaces
        String [] bagOfWords = lines.split("\\s+");

        for (int i = 0;i<bagOfWords.length;i++){
            int low = Math.max(i-k,0);
            int high = Math.min(i+k,bagOfWords.length-1);
            int kNeighbourHoodSumScore = 0;
            for (int j = low; j <= high; j++) {
                if (j != i) {
                    kNeighbourHoodSumScore +=  wordScore(bagOfWords[j],scoreOfLetters);

                }
            }

            word.set(bagOfWords[i]);
            context.write(word,new IntWritable(kNeighbourHoodSumScore));
        }

    }
}

