package cn.bit.tao.wordcount.spout;

import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

/**
 * @author  Tao wenjun
 * WordCountSpout:Ëæ»ú·¢Éä¾ä×Ó
 */

public class WordCountSpout extends BaseRichSpout {
	
	private SpoutOutputCollector collector;
	
	public static String[] sentences = new String[]{
			"Licensed to the Apache Software Foundation (ASF) under one",
			"or more contributor license agreements.  See the NOTICE file",
			"distributed with this work for additional information",
			"regarding copyright ownership.  The ASF licenses this file"};
    
	public void nextTuple() {
	    Random random = new Random();
		int temp = random.nextInt(sentences.length);
		collector.emit(new Values(sentences[temp]));
	}

	public void open(Map arg0, TopologyContext arg1, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}

}
