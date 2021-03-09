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

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.yarn.webapp.hamlet2.Hamlet;
import org.eclipse.jetty.webapp.MetaDataComplete;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TransferQueue;

import static org.eclipse.jetty.webapp.MetaDataComplete.True;

/**
 * Wordcount Reducer
 *
 * @author Data Dynamics
 * @version 0.1
 */
public class JoinReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
    }

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // key: ranker_id, value: ""
        // key: user_id, value: chat_text[]
        int isR = 0;
        StringBuilder chat_all = new StringBuilder();

        for (Text value : values) {
            String chat_data = value.toString();
            String check = chat_data.substring(0, 2);
            if (check.equals("R^")) {
                isR = 1;
            } else {
                chat_all.append(chat_data.substring(2));
            }
        }
        if (isR == 1 && chat_all.length() >= 1) {
            context.write(key, new Text("|"+chat_all.toString()));
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {

    }
}