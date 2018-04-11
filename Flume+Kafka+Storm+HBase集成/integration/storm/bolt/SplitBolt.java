package cn.bit.tao.integration.storm.bolt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 *@author  Tao wenjun
 *SplitBolt£º·Ö¸î¾ä×ÓµÄBolt
 */

public class SplitBolt extends BaseRichBolt {

	private OutputCollector collector;
	
	public void execute(Tuple tuple) {
		String sentence = tuple.getString(0);
		String[] words = sentence.split("\\s+");
		for(String word:words){
			word = word.toLowerCase();
			List a = new ArrayList();
			a.add(tuple);
			collector.emit(a,new Values(word));
		}
	}

	public void prepare(Map map, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

}
