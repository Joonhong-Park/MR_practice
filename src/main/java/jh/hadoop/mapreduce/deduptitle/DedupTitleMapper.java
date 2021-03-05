/*
 * Licensed to the Apache Software Foundation (ASF) under one
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
package jh.hadoop.mapreduce.deduptitle;

import io.datadynamics.bigdata.mapreduce.ChatLog;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Wordcount Mapper
 *
 * @author Data Dynamics
 * @version 0.1
 */
public class DedupTitleMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    private String delimiter;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        Configuration configuration = context.getConfiguration();
        delimiter = configuration.get("delimiter", ",");
        context.getCounter("CUSTOM_COUNT", "call mapper setup").increment(1);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String row = value.toString();
        String[] columns = row.split(delimiter);
        String bTitle = columns[ChatLog.b_title.ordinal()];
        String bStartTime = columns[ChatLog.b_start_time.ordinal()];
        context.write(new Text(bStartTime + "^" + bTitle), NullWritable.get());
        context.getCounter("CUSTOM_COUNT", "call mapper map").increment(1);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        context.getCounter("CUSTOM_COUNT", "call mapper cleanup").increment(1);
    }
}