/*
s * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jh.hadoop.mapreduce.sample.Join;

import jh.hadoop.mapreduce.sample.input.ChatLogInputFormat;
import jh.hadoop.mapreduce.sample.input.ZipCodec;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.MRJobConfig;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.ToolRunner;


import java.io.IOException;

/**
 * Wordcount 예제.
 *
 * @author Data Dynamics
 * @version 0.1
 */
public class JoinByKeyDriver extends org.apache.hadoop.conf.Configured implements org.apache.hadoop.util.Tool {

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new JoinByKeyDriver(), args);
        System.exit(res);
    }

    public int run(String[] args) throws Exception {
        GenericOptionsParser parser = new GenericOptionsParser(this.getConf(), args);
        String[] remainingArgs = parser.getRemainingArgs();
        Job job = Job.getInstance(this.getConf());

        parseArguments(remainingArgs, job);

        job.setJarByClass(JoinByKeyDriver.class);

        // Mapper & Reducer Class

        job.setReducerClass(JoinByKeyReducer.class);

        // Mapper Output Key & Value Type after Hadoop 0.20
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        // Reducer Output Key & Value Type
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // Partitioner
        job.setPartitionerClass(JoinByKeyPartitioner.class);
        
        // MultipleOutput
        MultipleOutputs.addNamedOutput(job, "userId", TextOutputFormat.class, Text.class, Text.class);
        LazyOutputFormat.setOutputFormatClass(job, TextOutputFormat.class);
        
        // Output Sperator
        job.getConfiguration().set(TextOutputFormat.SEPARATOR, "=");
        
        // Map Output -> gzip
//        job.getConfiguration().set(MRJobConfig.MAP_OUTPUT_COMPRESS, "true");
        job.getConfiguration().setBoolean(MRJobConfig.MAP_OUTPUT_COMPRESS, true);
        job.getConfiguration().set(MRJobConfig.MAP_OUTPUT_COMPRESS_CODEC, GzipCodec.class.getName());
        
        // Reducer output -> gzip
        job.getConfiguration().set(TextOutputFormat.COMPRESS, "true");
        job.getConfiguration().set(TextOutputFormat.COMPRESS_CODEC, GzipCodec.class.getName());
        
        // map input시 zip파일도 읽을 수 있도록 zip codec 추가
        job.getConfiguration().set("io.compression.codecs",
                job.getConfiguration().get("io.compression.codecs") + "," + ZipCodec.class.getName());

        // Run a Hadoop Job
        return job.waitForCompletion(true) ? 0 : 1;
    }

    private void parseArguments(String[] args, Job job) throws IOException {
        for (int i = 0; i < args.length; ++i) {
            if ("-inputone".equals(args[i])) {
                MultipleInputs.addInputPath(job, new Path(args[++i]), TextInputFormat.class, JoinByKeyMapper1.class);
            } else if ("-inputtwo".equals(args[i])) {
                // Chatting 파일은 직접만든 ChatLogInputFormat을 이용하여 read
                MultipleInputs.addInputPath(job, new Path(args[++i]), ChatLogInputFormat.class, JoinByKeyMapper2.class);
            } else if ("-output".equals(args[i])) {
                FileOutputFormat.setOutputPath(job, new Path(args[++i]));
            } else if ("-delimiter".equals(args[i])) {
                job.getConfiguration().set("delimiter", args[++i]);
            } else if ("-reducer".equals(args[i])) {
                job.setNumReduceTasks(Integer.parseInt(args[++i]));
            }
        }
    }
}