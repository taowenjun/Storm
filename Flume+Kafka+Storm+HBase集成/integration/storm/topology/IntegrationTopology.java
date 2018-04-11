package cn.bit.tao.integration.storm.topology;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import cn.bit.tao.integration.storm.bolt.SplitBolt;
import cn.bit.tao.integration.storm.bolt.WordCountBolt;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;

/**
 *@author  Tao wenjun
 *IntegrationTopology:整个集成操作的storm拓扑
 */

public class IntegrationTopology {

	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		String topic = "integration";
		ZkHosts zkHosts = new ZkHosts("master:2181,slave1:2181,slave2:2181");
		SpoutConfig spoutConfig = new SpoutConfig(zkHosts, topic, "", "fkshApp");
		List<String> zkServers = new ArrayList<String>();
		for(String host:zkHosts.brokerZkStr.split(",")){
			zkServers.add(host.split(":")[0]);
		}
		spoutConfig.zkServers = zkServers;
		spoutConfig.zkPort=2181;
		spoutConfig.socketTimeoutMs=60*1000*1000;
		spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		
		//创建Spout
		KafkaSpout spout = new KafkaSpout(spoutConfig);
		builder.setSpout("kafka-spout", spout);
		builder.setBolt("split-bolt", new SplitBolt()).shuffleGrouping("kafka-spout");
		builder.setBolt("wordcount-bolt", new WordCountBolt()).fieldsGrouping("split-bolt", new Fields("word"));
		
		Config conf = new Config();
		conf.setDebug(false);
		
		Properties properties = new Properties();
		properties.put("metadata.broker.list", "master:9098,slave1:9098");
        //ack机智 配置
		properties.put("request.required.acks", "1"); // 0  1 -1
		properties.put("serializer.class", "kafka.serializer.StringEnscoder");
        conf.put("kafka.broker.properties", properties);
        
        conf.setMaxTaskParallelism(3);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("wordcount", conf, builder.createTopology());
        try {
			Thread.sleep(600000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        cluster.shutdown();
	}
}
