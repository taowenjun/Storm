package cn.bit.tao.integration.kafka;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;



/**
 *@author  Tao wenjun
 *TestFlumeKafka：测试Flume与Kafka是否连接成功
 */

public class TestFlumeKafka {
	
	private static final String BROKER_LISR="10.108.21.2:9092,10.108.21.236:9092";
	
	private static final String STRING_DES="org.apache.kafka.common.serialization.StringDeserializer";

	public static void main(String[] args) {
		Map<String, Object> configs  = new HashMap<String, Object>();
		configs.put("bootstrap.servers", BROKER_LISR);
		configs.put("group.id", "test-consumer-group");
		configs.put("client.id","test");
	    configs.put("enable.auto.commit",true);
		configs.put("auto.commit.interval.ms", 1000);
		configs.put("key.deserializer", STRING_DES);
		configs.put("value.deserializer", STRING_DES);
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(configs);
		consumer.subscribe(Arrays.asList("integration"));
		
		while(true){
			ConsumerRecords<String, String> records = consumer.poll(100);
			for(ConsumerRecord<String, String> record:records){
				System.out.println(record.toString());
			}
		}

	}

}
