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

import java.io.IOException;
import java.util.ArrayList;

/**
 * Wordcount Reducer
 *
 * @author Data Dynamics
 * @version 0.1
 */
public class JoinByKeyReducer extends Reducer<Text, Text, Text, Text> {
    private String pre_flag;
    private StringBuilder chat_all;
    private String pre_id;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        pre_flag = "";
        pre_id = "";
    }

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {


        chat_all = new StringBuilder();
        String[] flag = key.toString().split("\\^");
        String id = flag[0];
        String type = flag[1];

        if (type.equals("A")) {
            pre_flag = "A";
            pre_id = id;
        } else {
            if (pre_flag.equals("A") && pre_id.equals(id)) {
                for (Text value : values) {
                    String chat_data = value.toString();
                    chat_all.append(chat_data).append("|");
                }
                context.write(new Text(id), new Text(chat_all.toString().substring(0,chat_all.length()-1)));
                pre_flag = "";
                pre_id = "";
            }
        }


    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {

    }
}