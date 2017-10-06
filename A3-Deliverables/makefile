HADOOP_HOME = /home/aditya/hadoop/hadoop-2.8.1
MY_CLASSPATH = $(HADOOP_HOME)/share/hadoop/common/hadoop-common-2.8.1.jar:$(HADOOP_HOME)/share/hadoop/mapreduce/hadoop-mapreduce-client-common-2.8.1.jar:$(HADOOP_HOME)/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.8.1.jar:out:.

all: build

build: compile jar

compile:
	javac -cp $(MY_CLASSPATH) -d out com/pdpmrd/a3/*.java
jar:	
	cd out; jar cfm MainDriver.jar Mainifest.txt com/pdpmrd/a3/*.class

run:
   $(HADOOP_HOME)/bin/hadoop jar MainDriver.jar $(REPETITIONS) $(K) input/big-corpus letterCountoutput output executiontimes.csv
   Rscript -e "rmarkdown::render('A3-Report.Rmd')"

clean:
   $(HADOOP_HOME)/bin/hdfs dfs -rm -r letterCountoutput;
   $(HADOOP_HOME)/bin/hdfs dfs -rm -r output;
   

gzip:
   -gzip input/books/*; gzip input/big-corpus/*

gunzip:
   -gunzip input/books/*; gunzip input/big-corpus/*
