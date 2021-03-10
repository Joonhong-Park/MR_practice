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

import jh.hadoop.mapreduce.ChatLog;
import jh.hadoop.mapreduce.sample.input.ChatLogInputFormat;
import jh.hadoop.mapreduce.sample.input.ChatLogWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Wordcount Mapper
 *
 * @author Data Dynamics
 * @version 0.1
 */
public class JoinByKeyMapper2 extends Mapper<LongWritable, ChatLogWritable, Text, Text> {

    private String delimiter;
    HashMap<String, String> chat_data = new HashMap<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
            }

    @Override
    protected void map(LongWritable key, ChatLogWritable value, Context context) throws IOException, InterruptedException {

        String chat_id = value.getUserId();
        String chat = value.getChatText();

        context.write(new Text(chat_id + "^B"), new Text(chat));
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {

    }
}