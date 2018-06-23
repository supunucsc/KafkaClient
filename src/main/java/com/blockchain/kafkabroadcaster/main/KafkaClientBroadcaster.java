package com.blockchain.kafkabroadcaster.main;

import com.blockchain.kafkabroadcaster.init.InitConfigValues;
import com.blockchain.kafkabroadcaster.server.Server;
import com.blockchain.kafkabroadcaster.server.WebsocketServer;
import com.blockchain.kafkabroadcaster.util.Util;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

public class KafkaClientBroadcaster {

    public static void main(String[] args) {

        Util.initializeVariables();
       WebsocketServer wss=new WebsocketServer();


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wss.start();

        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, InitConfigValues.KAFKA_HOSTIP+":"+InitConfigValues.KAFKA_PORT);
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, InitConfigValues.GROUPID);
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer(consumerConfig);
        TestConsumerRebalanceListener rebalanceListener = new TestConsumerRebalanceListener();
        consumer.subscribe(Collections.singletonList(InitConfigValues.KAFKA_TOPIC), rebalanceListener);

        while (true) {
            ConsumerRecords<byte[], byte[]> records = consumer.poll(1000);
            for (ConsumerRecord<byte[], byte[]> record : records) {

                System.out.printf("Received Message topic =%s, partition =%s, offset = %d, key = %s, value = %s\n", record.topic(), record.partition(), record.offset(), record.key(), record.value());
                wss.broadCastMessage( record.value()+"");
            }

            consumer.commitSync();
        }

    }

    private static class  TestConsumerRebalanceListener implements ConsumerRebalanceListener {
         public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            System.out.println("Called onPartitionsRevoked with partitions:" + partitions);
        }

         public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            System.out.println("Called onPartitionsAssigned with partitions:" + partitions);
        }
    }

}