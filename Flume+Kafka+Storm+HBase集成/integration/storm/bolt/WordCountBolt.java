package cn.bit.tao.integration.storm.bolt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

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
 *
 */

public class WordCountBolt extends BaseRichBolt {
	
	private OutputCollector collector;
	
	private static Configuration conf;
	
	private static HTable table;
	
	Map<String, Integer> counts = new HashMap<String, Integer>();

	public void execute(Tuple tuple) {
		String word = tuple.getString(0);
		Integer count = counts.getOrDefault(word, 0);
		count++;
		counts.put(word, count);
		//行健，随机生成一个
		UUID uuid = UUID.randomUUID();
		System.out.println("uuid->"+uuid);
		String rowKey = uuid.toString();
		//列族
		String columnFamily = "fksh";
		//标识符
		String identifier = "wc";
		Put put = new Put(Bytes.toBytes(rowKey));
		put.add(columnFamily.getBytes(), identifier.getBytes(), counts.toString().getBytes());
		
		try {
			table.put(put);
			System.out.println("Write to hbase");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		collector.emit(new Values(word,count));
	}

	public void prepare(Map map, TopologyContext context, OutputCollector collector) {
		this.collector=collector;
		conf=HBaseConfiguration.create();
		conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "master:2181,slave1:2181,slave2:2181");
        conf.set("hbase.master", "master" + ":60000");
        
        //表名
        String tableName = "storm_table";
        try {
			table=new HTable(conf, tableName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("result"));
	}

}
