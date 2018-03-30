package cn.bit.tao.testword.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 *@author  Tao wenjun
 *ExclamationBolt
 *execute方法在输入元组后面追加"!!!"
 */

public class ExclamationBolt extends BaseRichBolt {

	/**
	 * 版本信息
	 */
	private static final long serialVersionUID = 1L;
	
	OutputCollector _collector;

	public void execute(Tuple tuple) {
		this._collector.emit(tuple,new Values(tuple.getString(0)+"!!!"));
		this._collector.ack(tuple);
	}

	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this._collector=collector;
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

}
