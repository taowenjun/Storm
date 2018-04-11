package cn.bit.tao.integration.flume;

import java.util.Random;

import org.apache.flume.api.RpcClient;

/**
 *@author  Tao wenjun
 *FlumeRpcApp������Flume�ṩ��RpcClient��Flume������Ϣ
 */

public class FlumeRpcApp {

	public static void main(String[] args) {
		RpcClient client = FlumeRPCClient.createClient("10.108.21.2", 44444);
        Random rand = new Random();
        
        String[] sentences = new String[]{ "the cow jumped over the moon", "an apple a day keeps the doctor away",
                "four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature"
                ,"�ѵ� û�� ���� ��"};
        for(int i=0;i<10000;i++){
        	String data = sentences[rand.nextInt(sentences.length)];
        	FlumeRPCClient.sendData(client, data);
        	System.out.println("Send data-----"+data);
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        client.close();
	}

}
