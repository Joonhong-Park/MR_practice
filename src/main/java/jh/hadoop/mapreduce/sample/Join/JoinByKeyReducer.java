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

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Wordcount Reducer
 *
 * @author Data Dynamics
 * @version 0.1
 */
public class JoinByKeyReducer extends Reducer<Text, Text, Text, Text> {
    private static final Logger logger = LoggerFactory.getLogger(JoinByKeyReducer.class);

    private String pre_flag;
    private StringBuilder chat_all;
    private String pre_id;
    private MultipleOutputs<Text, Text> outputs;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        pre_flag = "";
        pre_id = "";
        chat_all = new StringBuilder();
        outputs = new MultipleOutputs<>(context);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        outputs.close();
    }

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        chat_all.setLength(0);
        String[] flag = key.toString().split("\\^");
        String id = flag[0];
        String type = flag[1];

        switch (type) {
            case "A":
                pre_flag = "A";
                pre_id = id;
                break;
            default:
                if (pre_flag.equals("A") && pre_id.equals(id)) {
                    for (Text value : values) {
                        String chat_data = value.toString();
                        chat_all.append(chat_data).append("|");
                    }
                    Text userId = new Text(id);
                    String chats = chat_all.substring(0, chat_all.length() - 1);
                    if (chats.isEmpty()) {
                        logger.info("chats = {}", chats);
                    }
                    Text value = new Text(chats);
                    //context.write(userId, value);
                    context.getCounter("CUSTOM", "reduce mo write").increment(1);
                    outputs.write("userId", userId, value, key.toString().replaceAll("\\^", "_"));
                    pre_flag = "";
                    pre_id = "";
                }
                break;
        }
    }
}