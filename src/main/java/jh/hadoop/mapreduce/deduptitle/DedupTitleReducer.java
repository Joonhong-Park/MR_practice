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
public class DedupTitleReducer extends Reducer<Text, NullWritable, Text, Text> {

    private String prevBStartTime;
    //    private String bTitles;
    private StringBuilder bTitles;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        prevBStartTime = "";
        bTitles = new StringBuilder();
        context.getCounter("CUSTOM_COUNT", "call reducer setup").increment(1);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        if (bTitles.length() != 0) {
            context.write(new Text(prevBStartTime), new Text(bTitles.substring(0, bTitles.length() - 1)));
        }
        context.getCounter("CUSTOM_COUNT", "call reducer cleanup").increment(1);
    }

    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        String[] tokens = key.toString().split("\\^");
        String bStartTime = tokens[0];
        String bTitle = tokens[1];

        if (!prevBStartTime.equals(bStartTime)) {
            if (bTitles.length() != 0) {
                context.write(new Text(prevBStartTime), new Text(bTitles.substring(0, bTitles.length() - 1)));
            }
            prevBStartTime = bStartTime;
            bTitles.setLength(0);
        }
        bTitles.append(bTitle).append("|");
        context.getCounter("CUSTOM_COUNT", "call reducer reduce").increment(1);
    }
}