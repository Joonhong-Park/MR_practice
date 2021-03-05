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

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


/**
 * Wordcount Reducer
 *
 * @author Data Dynamics
 * @version 0.1
 */
public class DedupTitleCombiner extends Reducer<Text, NullWritable, Text, NullWritable> {

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        context.getCounter("CUSTOM_COUNT", "call combiner setup").increment(1);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        context.getCounter("CUSTOM_COUNT", "call combiner cleanup").increment(1);
    }

    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        context.write(key, NullWritable.get());
        for (NullWritable value : values) {
//            String s = value.toString();
        }
        context.getCounter("CUSTOM_COUNT", "call combiner reduce").increment(1);
    }
}