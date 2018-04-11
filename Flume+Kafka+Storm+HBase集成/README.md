Flume+Kafka+Storm+HBase集成
代码在integration文件夹下
#Flume
详细配置见Flume配置文件夹下的integration.properties
采用Flume提供的RpcClient向Flume发送消息


#Kafka
创建Kafka主题并查看主题信息
[root@master kafka]# bin/kafka-topics.sh --create --zookeeper master:2181,slave1:2181,slave2:2181 --topic integration --partitions 2 --replication-factor 2
Created topic "integration".
[root@master kafka]# bin/kafka-topics.sh --describe --zookeeper master:2181,slave1:2181,slave2:2181 --topic integration
Topic:integration       PartitionCount:2        ReplicationFactor:2     Configs:
        Topic: integration      Partition: 0    Leader: 1       Replicas: 1,0   Isr: 1,0
        Topic: integration      Partition: 1    Leader: 0       Replicas: 0,1   Isr: 0,1

#Storm
Spout采用KafkaSpout
在Bolt中向HBase表中插入数据

#HBase		
事先创建表 “storm_table","fksh"
hbase(main):002:0> create "storm_table","fksh"
0 row(s) in 2.5670 seconds

=> Hbase::Table - storm_table
hbase(main):003:0> list
TABLE                                                                                                                                                            
storm_table                                                                                                                                                      
1 row(s) in 0.0200 seconds

=> ["storm_table"]

hbase(main):006:0> describe "storm_table"
Table storm_table is ENABLED                                                                                                                                     
storm_table                                                                                                                                                      
COLUMN FAMILIES DESCRIPTION                                                                                                                                      
{NAME => 'fksh', BLOOMFILTER => 'ROW', VERSIONS => '1', IN_MEMORY => 'false', KEEP_DELETED_CELLS => 'FALSE', DATA_BLOCK_ENCODING => 'NONE', TTL => 'FOREVER', COM
PRESSION => 'NONE', MIN_VERSIONS => '0', BLOCKCACHE => 'true', BLOCKSIZE => '65536', REPLICATION_SCOPE => '0'}                                                   
1 row(s) in 0.2850 seconds


Storm程序运行后：
hbase(main):017:0> count "storm_table"
Current count: 1000, row: 22911584-4deb-4521-b7ac-f4d0814ba3e2                                                                                                   
Current count: 2000, row: 45d5bd1b-5b13-4eae-a2dc-08025d007ebc                                                                                                   
Current count: 3000, row: 6735d314-b365-45eb-8457-daea1c66f2fd                                                                                                   
Current count: 4000, row: 8d3dc19c-2913-48a8-bf1a-e7e3707b5fb5                                                                                                   
Current count: 5000, row: ae17bbd0-00e1-41fa-a716-8ed3b282b558                                                                                                   
Current count: 6000, row: d1e5dd0e-2f1d-46e7-aef2-e49d15b4ea17                                                                                                   
Current count: 7000, row: f6d0acf1-c6d4-4cc0-937c-4d6fe548283f                                                                                                   
7249 row(s) in 1.6520 seconds
