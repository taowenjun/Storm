package cn.bit.tao.bolt;

import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

/**
 *@author  Tao wenjun
 *My Kafka Bolt
 */

public class MyKafkaBolt implements IBasicBolt {

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	public void execute(Tuple input, BasicOutputCollector collector) {
		String kafkaMsg = input.getString(0);
		System.out.println("bolt:"+kafkaMsg);
	}

	public void prepare(Map arg0, TopologyContext arg1) {
		// TODO Auto-generated method stub
		
	}

}
