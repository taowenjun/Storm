package cn.bit.tao.integration.flume;

import java.nio.charset.Charset;

import org.apache.commons.lang.CharSet;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.event.EventBuilder;

/**
 *@author  Tao wenjun
 *FlumeRPCClient:Flume�ͻ�����
 */

public class FlumeRPCClient {
	
	/**
	 * ����Flume�ͻ���
	 * @param hostname��Flume������IP
	 * @param port��Flume�������˿�
	 * @return��������Flume�ͻ���
	 */
	public static RpcClient createClient(String hostname,Integer port){
		return RpcClientFactory.getDefaultInstance(hostname, port);
	}
	
	/**
	 * �ر�Flume�ͻ���
	 * @param client
	 */
	public static void closeClient(RpcClient client){
		client.close();
	}
	
	/**
	 * ʹ��Flume��������
	 * @param client��Flume�ͻ���
	 * @param data����Ҫ���͵�����
	 */
	public static void sendData(RpcClient client,String data){
		Event event = EventBuilder.withBody(data, Charset.forName("UTF-8"));
		try {
			client.append(event);
		} catch (EventDeliveryException e) {
			e.printStackTrace();
		}
	}
}
