package jh.hadoop.mapreduce.deduptitle;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class DedupTitlePartitioner extends Partitioner<Text, NullWritable> {
    @Override
    public int getPartition(Text text, NullWritable nullWritable, int reducerNum) {
        String bStartTime = text.toString().split("\\^")[0];
        return Math.abs(bStartTime.hashCode()) % reducerNum;
    }
}
