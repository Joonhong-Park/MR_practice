package jh.hadoop.mapreduce.sample.Join;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class JoinByKeyPartitioner extends Partitioner<Text, Text> {
    @Override
    public int getPartition(Text key, Text value, int reducerNum) {
        String id = key.toString().split("\\^")[0];
        return Math.abs(id.hashCode()) % reducerNum;
    }
}
