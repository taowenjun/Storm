#Kafka+Storm整合

producer：Kafka生产者，负责向Kafka输入数据流

bolt：简单起见，在Storm中负责输出Kafka输入的数据

topology：Storm运行的拓扑，其中Spout为Kafka-Storm整合包提供的KafkaSpout类，通过SpoutConfig配置其信息
