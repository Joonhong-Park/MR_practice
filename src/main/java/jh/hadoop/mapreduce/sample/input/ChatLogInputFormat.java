package jh.hadoop.mapreduce.sample.input;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class ChatLogInputFormat extends FileInputFormat<LongWritable, ChatLogWritable> {

    @Override
    public RecordReader<LongWritable, ChatLogWritable> createRecordReader(InputSplit split, TaskAttemptContext context) {
        return new ChatLogRecordReader();
    }
}
