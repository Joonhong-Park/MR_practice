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

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

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
        Iterator<Text> iterator = values.iterator();
        if (iterator.hasNext()) {
            String first = iterator.next().toString();
            String check = first.substring(0, 2);
            if (check.equals("R^")) {
                if(iterator.hasNext()){
                    String second = iterator.next().toString();
                    String check2 = second.substring(0,2);
                    if(check2.equals("C^")){
                        context.write(key, new Text(second.substring(2)));
                    }
                }
            }else if(check.equals("C^")){
                if(iterator.hasNext()){
                    String third = iterator.next().toString();
                    String check3 = third.substring(0,2);
                    if(check3.equals("R^")){
                        context.write(key, new Text(first.substring(2)));
                    }
                }
            }
        }
    }
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {

    }
}