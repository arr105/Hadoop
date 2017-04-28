

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.FileInputFormat;
import org.apache.hadoop.mapreduce.FileOutputFormat;


import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

import org.apache.hadoop.io.LongWritable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class NgramMapReduce {

public static class TokenizerMapper
extends Mapper<Object, Text, Text, IntWritable>{

privatefinal static IntWritable one = new IntWritable(1);
private Text word = new Text();

public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
StringTokenizer itr = newStringTokenizer(value.toString());
Scanner in = new Scanner(System.in);
int gramSize=0;

System.out.println("Enter gram size:");
gramSize=in.nextInt();
ArrayList<String> gramList=new ArrayList<String>();

//String str1[]=str.split("\\s+");

String str1[]=line.split("\\s+");

for(int k=0;k<str1.length;k++){
//System.out.println(str1[k]);
//for(int i=0;(i+gramSize)<=str.length();i++){
for(int i=0;(i+gramSize)<=str1[k].length();i++){
String temp=str1[k].substring(i, i+gramSize);
//System.out.println(temp);
gramList.add(temp);
}
}
       
              //StringTokenizer tokenizer = new StringTokenizer(line);
	             //while (tokenizer.hasMoreTokens()) {
		             for(String gram:gramList){ 
			     word.set(gram);
			              context.write(word, one);
				             }
					          }
						     }

						     public static class IntSumReducer
						     extends Reducer<Text,IntWritable,Text,IntWritable>{
						     private IntWritable result = new IntWritable();

						     public void reduce(Text key, Iterable<IntWritable> values,
						        Context context) throws IOException, InterruptedException {
							    int sum = 0;
							           while (values.hasNext()) {
								            sum += values.next().get();
									           }
										      result.set(sum);
										      context.write(key, result);
										         }
											 }   

											 public static void main(String[] args) throws IOException {

											      Configuration conf = new Configuration ();
											       Job job = Job.getInstance(conf, "ngram");
											        JobConf conf = new JobConf(NgramMapReduce.class);
												     job.setJarByClass(NgramMapReduce.class);
												      job.setMapperClass(TokenizerMapper.class);
												       job.setCombinerClass(IntSumReducer.class);
												        job.setReducerClass(IntSumReducer.class);
													         job.setOutputKeyClass(Text.class);
														          job.setOutputValueClass(IntWritable.class);
															           FileInputFormat.addInputPath(job, new Path(args[0]));
																            FileOutputFormat.setOutputPath(job, new Path(args[1]));
																	             System.exit(job.waitForCompletion(true) ? 0 : 1);
																		      
																		       
																		        
																			 
																			      conf.setJobName("NGram");

																			           conf.setOutputKeyClass(Text.class);
																				        conf.setOutputValueClass(IntWritable.class);

																					     // conf.setMapperClass(Map.class);
																					          // conf.setCombinerClass(Reduce.class);
																						       // conf.setReducerClass(Reduce.class);

																						            conf.setInputFormat(TextInputFormat.class);
																							         conf.setOutputFormat(TextOutputFormat.class);

																								      // FileInputFormat.setInputPaths(conf, new Path(args[0]));
																								           // FileOutputFormat.setOutputPath(conf, new Path(args[1]));

																									        // JobClient.runJob(conf);
																										   

																										   }

																										   }


																										   // public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
																										        // private final static IntWritable one = new IntWritable(1);
																											     // private Text word = new Text();

																											          // public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
																												         // String line = value.toString();
																													        
																														       // Scanner in = new Scanner(System.in);
																														              
																															             // //String str="Hello how are you hello";
																																     // int gramSize=0;

																																     // System.out.println("Enter gram size:");
																																     // gramSize=in.nextInt();
																																     // ArrayList<String> gramList=new ArrayList<String>();

																																     // //String str1[]=str.split("\\s+");

																																     // String str1[]=line.split("\\s+");

																																     // for(int k=0;k<str1.length;k++){
																																     // //System.out.println(str1[k]);
																																     // //for(int i=0;(i+gramSize)<=str.length();i++){
																																     // for(int i=0;(i+gramSize)<=str1[k].length();i++){
																																     // String temp=str1[k].substring(i, i+gramSize);
																																     // //System.out.println(temp);
																																     // gramList.add(temp);
																																     // }
																																     // }
																																            
																																	           // //StringTokenizer tokenizer = new StringTokenizer(line);
																																		          // //while (tokenizer.hasMoreTokens()) {
																																			          // for(String gram:gramList){ 
																																				  // word.set(gram);
																																				           // output.collect(word, one);
																																					          // }
																																						       // }
																																						          // }


																																							  // public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
																																							       // public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
																																							           // int sum = 0;
																																								          // while (values.hasNext()) {
																																									           // sum += values.next().get();
																																										          // }
																																											         // output.collect(key, new IntWritable(sum));
																																												      // }
																																												         // }






















































