package com.pdpmrd.a3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/*
    Author : Aditya Kammardi Sathyanarayan
 */


public class A3Util {

    private static final String NEW_LINE_CHARACTER = "\n";
    private static final String COMMA_CHARACTER = ",";
    private static final String LOGHEADING = "Overall";

   /*
        Reads the output of letter count job and returns a string representation of the output
    */

    public static String letterCountString(String filePath) throws Exception {


        String uri = filePath;
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        FSDataInputStream in = null;
        StringBuilder sb = new StringBuilder();
        try {

            in = fs.open(new Path(uri));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            IOUtils.copyBytes(in, byteArrayOutputStream, 4096, false);
            String fileContents = byteArrayOutputStream.toString();
            String [] tokens = fileContents.split("\\s+");

            for(int i = 1 ;i<tokens.length;i+=2){
                sb.append(tokens[i]);
                sb.append(" ");
            }


        } finally {
            IOUtils.closeStream(in);
        }
        return sb.toString();
    }

    /*
        Computes letterScores from the String representation of the letter-count output
     */

    public static long [] computeLetterScores(String s){

        long [] scores = new long[26];
        String [] tokens = s.split("\\s+");
        Long sum = 0L;
        for(int i=0,j=0;i<tokens.length;i+=1,j+=1){
            long score = Long.parseLong(tokens[i]);
            scores[i] = score;
            sum+=score;
        }
        for (int i = 0;i<scores.length;i++){
            double popularity = scores[i] * 100.0 / sum;
            scores[i] = scoreOfLetterFromPopulariy(popularity);

        }

        return scores;
    }

    /*
        utility method to compute score
     */

    public static int scoreOfLetterFromPopulariy(double popularity){

        if (popularity >= (double)10)
            return 0;
        if (popularity >= (double)8 && popularity <(double)10)
            return 1;
        if (popularity >= (double)6 && popularity < (double)8)
            return  2;
        if(popularity >= (double)4 && popularity < (double)6)
            return 4;
        if (popularity >= (double)2 && popularity < (double)4)
            return 8;
        if (popularity >= (double)1 && popularity < (double)2)
            return 16;
        else
            return 32;
    }

    public static long[] stringToArray(String s){
        long res [] = new long[26];
        String [] scores = s.split("\\s+");
        for(int i =1,j=0;i<scores.length;i+=2,j++){
            res[j] = Long.parseLong(scores[i]);
        }
        return res;
     }

     public static String arrayToString(long [] scores){

        StringBuilder res  = new StringBuilder();
        int i = 0;
        for (long score : scores){
            char c = (char)(i+(int)'a');
            res.append(Character.toString(c));
            res.append(" ");
            res.append(Long.toString(score));
            res.append(" ");
            i+=1;
        }
        return res.toString();
     }

    /*
        Method to write the execution times into a csv
     */
    public static void logWrite(ArrayList<Long> times, String logFileName){
        FileWriter logWriter = null;
        try{
            logWriter = new FileWriter(logFileName);
            logWriter.append(LOGHEADING);
            logWriter.append(NEW_LINE_CHARACTER);


            for (Long time : times){
                logWriter.append(time.toString());
                logWriter.append(NEW_LINE_CHARACTER);
            }
        }
        catch (IOException e){
            System.out.println(e.toString());

        }
        finally {
            try{
                logWriter.flush();
                logWriter.close();
            }catch (IOException e){
                System.out.println(e.toString());
            }
        }
    }


    public static void main(String[] args) throws Exception{
        long [] scoreLetters = computeLetterScores(letterCountString("output1/part-r-00000"));
        String s = arrayToString(scoreLetters);
        scoreLetters = stringToArray(s);
        for (int i = 0;i< scoreLetters.length;i++){
            System.out.println(scoreLetters[i]);
        }

    }
}
