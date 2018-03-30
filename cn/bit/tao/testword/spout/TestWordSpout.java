package cn.bit.tao.testword.spout;

import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

/**
 *@author  Tao wenjun
 *TestWord Spout
 *nextTuple����ÿִ��һ��˯��100ms,��words�ַ������������ȡ��һ���ַ��������ȥ��
 */

public class TestWordSpout extends BaseRichSpout {

	/**
	 * �汾��Ϣ
	 */
	private static final long serialVersionUID = 1L;
	
	SpoutOutputCollector _collector;

	public void nextTuple() {
		Utils.sleep(100);
		final String[] words = new String[]{"nathan","mike","jackson","golda","bertels"};
		final Random rand = new Random();
		final String word = words[rand.nextInt(words.length)];
		_collector.emit(new Values(word));
	}

	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		_collector=collector;
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

}
