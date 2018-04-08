package cn.bit.tao.wordcount.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * @author  Tao wenjun
 * SplitSentenceBolt:将句子切分成单词
 */

public class SplitSentenceBolt extends BaseRichBolt {

	/**
	 * 版本信息
	 */
	private static final long serialVersionUID = 1L;
	
	private OutputCollector collector;

	public void execute(Tuple tuple) {
		String sentence = tuple.getString(0);
		String[] words = sentence.split(" ");
        for(String word:words){
            collector.emit(tuple, new Values(word));	
        }
        collector.ack(tuple);
	}

	public void prepare(Map arg0, TopologyContext arg1, OutputCollector collector) {
		this.collector=collector;
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

}
