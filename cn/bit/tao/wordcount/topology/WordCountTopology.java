package cn.bit.tao.wordcount.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import cn.bit.tao.wordcount.bolt.CountBolt;
import cn.bit.tao.wordcount.bolt.SplitSentenceBolt;
import cn.bit.tao.wordcount.spout.WordCountSpout;

/**
 *@author  Tao wenjun
 *WordCountTopology
 */

public class WordCountTopology {

	public static void run(){
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("sentences", new WordCountSpout());
		builder.setBolt("words", new SplitSentenceBolt()).shuffleGrouping("sentences");
		builder.setBolt("wordcount", new CountBolt()).fieldsGrouping("words", new Fields("word"));
	
		Config config = new Config();
		config.setDebug(true);
		
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("wordcount", config, builder.createTopology());
		Utils.sleep(10000);
		cluster.killTopology("wordcount");
		cluster.shutdown();
	}
}
