package cn.bit.tao.producer;

import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import cn.bit.tao.producer.StockQuotationInfo;

/**
 *@author  tao wenjun
 *���߳�������
 */

public class QuotationProducer {

	private static final Logger LOG=Logger.getLogger(QuotationProducer.class);
	
	/** ����ʵ��������Ϣ������ */
	private static final int MSG_SIZE=10000;
	
	/** ��������  */
	private static final String TOPIC="stock-quotation";
	
	/** Kafka��Ⱥ */
	private static final String BROKER_LIST="master:9092,slave1:9092,slave2:9092";
	
	/**  ������ */
	private static KafkaProducer<String, String> producer = null;
	
	static{
		//1����������ʵ����KafkaProducer��Properties��Ϣ
		Properties configs = initConfig();
		//2����ʼ��һ��KafkaProducer
		producer = new KafkaProducer<String, String>(configs);
	}
	
	/**
	 * ��ʼ��Kafka����	
	 * @return ������Ϣ
	 */
	private static Properties initConfig(){
		Properties properties = new Properties();
		//Kafka broker�б�
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
		//�������л���
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		return properties;
	}
	
	/**
	 * ������Ʊ������Ϣ
	 * @return һ����Ʊ������Ϣ
	 */
	private static StockQuotationInfo createQuotationInfo(){
		StockQuotationInfo quotationInfo = new StockQuotationInfo();
		//�������1��10֮���������Ȼ����600100�����ɹ�Ʊ����
		Random random = new Random();
		Integer stockCode = 600100+random.nextInt(10);
		//�������һ��0��1֮��ĸ�����
		float ran = (float)Math.random();
		//������ͣ����
		if(ran/2<0.4){
			ran=-ran;
		}
		DecimalFormat decimalFormat = new DecimalFormat(".00");
	    quotationInfo.setCurrentPrice(Float.valueOf(decimalFormat.format(11+ran))); //�������¼���11Ԫ����
	    quotationInfo.setPreClosePrice(11.80f);//�����������̼�Ϊ�̶�ֵ
	    quotationInfo.setOpenPrice(11.5f);//���ÿ��̼�
	    quotationInfo.setLowPrice(10.5f);//������ͼ�
	    quotationInfo.setHighPrice(12.5f);//������߼�
	    quotationInfo.setStockCode(stockCode.toString());//���ù�Ʊ����
	    quotationInfo.setTradeTime(System.currentTimeMillis());//���ý���ʱ��
	    quotationInfo.setStockName("��Ʊ-"+stockCode);//���ù�Ʊ����
	    return quotationInfo;
	}
	
	public static void main(String[] args) {
		ProducerRecord<String, String> record = null;
		StockQuotationInfo quotationInfo = null;
		try{
			int num=0;
			for(int i=0;i<MSG_SIZE;i++){
				quotationInfo = createQuotationInfo();
				System.out.println(quotationInfo);
				record=new ProducerRecord<String, String>(TOPIC, null,quotationInfo.getTradeTime(),
						quotationInfo.getStockCode(),quotationInfo.toString());
				producer.send(record);
				if(num++%10==0){
					Thread.sleep(2000L);
				}
			}
		}catch(InterruptedException e){
			LOG.error("send message occurs exception",e);
		}finally{
			producer.close();
		}
	}

}
