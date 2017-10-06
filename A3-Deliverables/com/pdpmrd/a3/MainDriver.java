package com.pdpmrd.a3;

import java.util.ArrayList;

/*
    Author : Aditya Kammardi Sathyanarayan
 */

/*
    Expects 6 arguments
    1 - number of iterations
    2 - K
    3 - input hdfs direcotry which has the input files extracted
    4 - Letter count output directory
    5 - output directory
    6 - path to csv file which logs the execution times
 */
public class MainDriver {
    public static void main(String []args) throws Exception{
        if (args.length <6){
            System.err.println("Enter valid input");
            System.exit(1);
        }

        Integer iterations = Integer.parseInt(args[0]);
        ArrayList<Long> exectuionTimes = new ArrayList<Long>();

        for (int i = 0;i<iterations;i++){
            long d1 = System.currentTimeMillis();
            //passing the current iteration number to the job drivers
            args[1] = Integer.toString(i);
            if(LetterCountDriver.drive(args)){
                NeighbourhoodDriver.drive(args);
            }
            long d2 = System.currentTimeMillis();
            exectuionTimes.add(d2-d1);
        }
        A3Util.logWrite(exectuionTimes,args[5]);
    }
}
