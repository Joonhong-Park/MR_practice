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
package jh.hadoop.mapreduce.sample.Join;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import jh.hadoop.mapreduce.ChatLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Wordcount Mapper
 *
 * @author Data Dynamics
 * @version 0.1
 */
public class JoinMapper2 extends Mapper<LongWritable, Text, Text, Text> {

    private String delimiter;
    HashMap<String, String> chat_data = new HashMap<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        Configuration configuration = context.getConfiguration();
        delimiter = configuration.get("delimiter", "\t");
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String row = value.toString();
        String[] columns = row.split(delimiter);
        String chat_id = columns[ChatLog.user_id.ordinal()];
        String chat = columns[ChatLog.chat_text.ordinal()];

        if(!chat_data.containsKey(chat_id)){
            chat_data.put(chat_id, "C^"+chat);
        }else{
            String prev_data = chat_data.get(chat_id);
            chat_data.put(chat_id, prev_data + "|" + chat);
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {

        for (Map.Entry<String, String> entry : chat_data.entrySet()) {
            String user_id = entry.getKey();
            String chat_all = entry.getValue();

            context.write(new Text(user_id), new Text(chat_all));
        }
    }
}