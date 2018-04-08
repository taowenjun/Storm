package cn.bit.tao.wordcount.bolt;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * @author  Tao wenjun
 * CountBolt:对单词进行计数
 */

public class CountBolt extends BaseBasicBolt{

	/**
	 * 版本信息
	 */
	private static final long serialVersionUID = 1L;
	
	Map<String,Integer> counts = new HashMap<String, Integer>();


	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word","count"));
	}

	public void execute(Tuple tuple, BasicOutputCollector collector) {
		String word = tuple.getString(0);
		Integer count = counts.get(word);
		if(count==null){
			count=0;
		}
		count++;
		counts.put(word, count);
		//System.out.println(word+"-->"+count);
		collector.emit(new Values(word,count));
	}
}
