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
package jh.hadoop.mapreduce.sample;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Wordcount Mapper
 *
 * @author Data Dynamics
 * @version 0.1
 */
public class WhereCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private String delimiter;
    private String where;
    private String what;
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        Configuration configuration = context.getConfiguration();
        delimiter = configuration.get("delimiter", ",");
        where = configuration.get("where", "0");
        what = configuration.get("what");
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String row = value.toString();
        String[] columns = row.split(delimiter);
        int index = Integer.parseInt(where);
        context.write(new Text(what), new IntWritable(1));
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
    }
}