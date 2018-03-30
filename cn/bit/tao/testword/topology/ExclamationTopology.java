package cn.bit.tao.testword.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import cn.bit.tao.testword.bolt.ExclamationBolt;
import cn.bit.tao.testword.spout.TestWordSpout;

/**
 *@author  Tao wenjun
 *ExclamationTopology
 */

public class ExclamationTopology {
	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("word", new TestWordSpout(),10);
		builder.setBolt("exclaim1", new ExclamationBolt(),3).shuffleGrouping("word");
		builder.setBolt("exclaim2", new ExclamationBolt(),2).shuffleGrouping("exclaim1");
		
		Config config = new Config();
		config.setDebug(true);
		
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("test", config, builder.createTopology());
		Utils.sleep(10000);
		cluster.killTopology("test");
		cluster.shutdown();
		
	}
}
