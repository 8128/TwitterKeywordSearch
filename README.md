# Twitter Keyword Search

[![Build Status](https://travis-ci.com/8128/TwitterKeywordSearch.svg?branch=master)](https://travis-ci.com/8128/TwitterKeywordSearch)

[Final slide](https://docs.google.com/presentation/d/13qQ6kA9aOH3ch5gzNk5sPxqQyETM0DrE2maC5a0VaNY/edit?usp=sharing)

The Twitter Keyword Search is a Springboot web application for twitter keyword searching. The folder is twSearch, and it is running at

 [Twitter Keyword Search](http://www.tty8128.com)

The Twitter Keyword Search CMD is a simple maven command line application for twitter keyword searching. The folder is twSearchCmd

## Quick Start

### Command line tool

Simply use the command in the folder where you place my twSearchCmd-1.0-SNAPSHOT.jar file

```shell
java -jar twSearchCmd-2.0-SNAPSHOT.jar
```

And then follow the instructions in the application.

### Springboot Application

It is complex to run it by yourself, you can access the website at:

[Twitter Search](http://www.tty8128.com)

If you still want to run it yourself, firstly you need to find the file twSearch/src/main/resources/application_example.yml  and rename the file to application.yml and change the properties inside to run it with your MySQL settings. 

You also need to create tables using sql queries in twSearch/src/main/resources/all.sql  Please do not forget to upload a CSV file to the tw_search database's t_twitter table. 

Then you need to complie the maven project using the command below:

```shell
mvn clean
mvn install
```

You will find the jar was generated at /twSearch/target. Using the jar with:

```shell
java -jar twSearch-1.0.2-SNAPSHOT.jar
```

You can access the website now using port 8080. If you are using your local pc, then the address should be localhost:8080 or 127.0.0.1:8080

## Implementation

### Word Matching

The keywords will be stored into HashSet, each for O(1) time complexity. Split the twitter into words, and iterate through every single word, use the contains function to check whether the word match any word inside of the hashset, each word for O(1) time complexity. An relevance value will be generated using the number of matching.

### Heap Sort

Initialize a new PriorityQueue(which implements the heap in Java), set the size to the needed size, and override the Comparator to compare them with the relevance value, and make it a min heapIterate through all the twitters and compute their relevance values. When the heap size reaches the needed size, and after that the new twitter’s relevance value is larger than the min value in the heap, then poll out the min twitter and add the new twitter

### Bucket Sort

Because the twitter’s length is smaller then 140, the largest relevance value will not be larger than 140, so buckets of 140 will be initialized. Iterate through all the twitter and calculate the relevance between the keywords and twitters, then use it to store the twitter to the bucket[relevance value]Finally, iterate from the last index of the bucket, add twitters until the list reaches the needed size

### Reverse Index

After load all data to list, iterate through all the twitters and store them to a HashMapHashMap<String, HashMap<Integer, Integer>> 

First Map: Key - Word / Value - Second HashMap

Second Map: Key - index of twitter in the data list / Value - this word’s frequency

When keywords come in, new a HashMap to store twitter and overall frequency, and compare frequency using Java’s Collections.sort and BucketSort

## Architecture

### CMD

The CMD application is rather simple. There is only one class and when user starts the application the constructor will load the file. Then bucket sort and heap sort will be executed. 

### Springboot application

The springboot application simply use the MVC architecture. It was done by the MyBatis + MySQL + thymeleaf in my case.

#### Database

The backend database is MySQL, it has pre-stored CSV file for 200000+ twitters. It can be used as demo. Also, users are welcomed to upload their own databases for twitter searching. Everytime users upload a new file to the application, the temp table will be cleared and new data will be sent in.

- t_twitter_internal
- t_twitter_temp

The project is using MyBatis as the solution for MySQL reading. TwitterData object will be generated when the tables are being read.

#### Template Engine:

Thymeleaf is chosen to be the template engine of this project.
