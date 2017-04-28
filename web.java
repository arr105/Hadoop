import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public final class web {
    private final static IntWritable ONE = new IntWritable(1);

    public final static void main(final String[] args) throws Exception {
        final Configuration conf = new Configuration();

        final Job job = new Job(conf, "web");
        job.setJarByClass(web.class);

        job.setOutputKeyClass(web.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(webMap.class);
        job.setCombinerClass(webReduce.class);
        job.setReducerClass(webReduce.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }

    public static final class webMap extends Mapper<LongWritable, Text, Text, IntWritable> {
        private final Text mapKey = new Text();

        public final void map(final LongWritable key, final Text value, final Context context)
                throws IOException, InterruptedException {
            final String line = value.toString();
            final String[] data = line.trim().split(" ");
            if (data.length > 1) {
                final String ipAddress = data[0];
                mapKey.set(ipAddress);
                context.write(mapKey, ONE);
            }
        }
    }

    public static final class webReduce extends Reducer<Text, IntWritable, Text, IntWritable> {

        public final void reduce(final Text key, final Iterable<IntWritable> values, final Context context)
                throws IOException, InterruptedException {
            int count = 0;
            for (final IntWritable val : values) {
                count += val.get();
            }
            context.write(key, new IntWritable(count));
        }
    }
}