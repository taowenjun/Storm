package cn.bit.tao.topoloty;

import java.util.ArrayList;
import java.util.List;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import cn.bit.tao.bolt.MyKafkaBolt;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;

/**
 *@author  Tao wenjun
 *Kafka-Storm Topology
 */

public class KafkaStormTopology {

	public static void main(String[] args) {
		
		String topic = "stock-quotation";
		String BROKER_LIST="master:2181,slave1:2181,slave2:2181";
		ZkHosts zkHosts = new ZkHosts(BROKER_LIST);
		
		SpoutConfig spoutConfig = new SpoutConfig(zkHosts,topic,"","MyTrack");
		
		List<String> zkServers = new ArrayList<String>();
		zkServers.add("master");
		zkServers.add("slave1");
		zkServers.add("slave2");
		
		spoutConfig.zkServers=zkServers;
		spoutConfig.zkPort=2181;
		spoutConfig.socketTimeoutMs=60*1000;
		spoutConfig.scheme=new SchemeAsMultiScheme(new StringScheme());
		
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", new KafkaSpout(spoutConfig));
		builder.setBolt("bolt", new MyKafkaBolt()).shuffleGrouping("spout");
		
		Config conf = new Config();
		conf.setDebug(false);

		LocalCluster localCluster = new LocalCluster();
		localCluster.submitTopology("kafka-storm", conf, builder.createTopology());
	}

}
