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
 *FlumeRPCClient:Flume客户端类
 */

public class FlumeRPCClient {
	
	/**
	 * 创建Flume客户端
	 * @param hostname：Flume服务器IP
	 * @param port：Flume服务器端口
	 * @return：创建的Flume客户端
	 */
	public static RpcClient createClient(String hostname,Integer port){
		return RpcClientFactory.getDefaultInstance(hostname, port);
	}
	
	/**
	 * 关闭Flume客户端
	 * @param client
	 */
	public static void closeClient(RpcClient client){
		client.close();
	}
	
	/**
	 * 使用Flume发送数据
	 * @param client：Flume客户端
	 * @param data：需要发送的数据
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
