package com.blockchain.kafkabroadcaster.util;

import com.blockchain.kafkabroadcaster.init.InitConfigValues;

public class Util {
    public static void initializeVariables(){

            if(System.getenv("KAFKA_HOSTIP")!=null){

                InitConfigValues.KAFKA_HOSTIP=System.getenv("KAFKA_HOSTIP");
            }else{

                InitConfigValues.KAFKA_HOSTIP="10.25.121.132";

            }


            if(System.getenv("KAFKA_PORT")!=null){

                InitConfigValues.KAFKA_PORT=Integer.parseInt(System.getenv("KAFKA_PORT"));
            }else{

                InitConfigValues.KAFKA_PORT=9020;

            }


            if(System.getenv("KAFKA_TOPIC")!=null){

                InitConfigValues.KAFKA_TOPIC=System.getenv("KAFKA_TOPIC");
            }else{

                InitConfigValues.KAFKA_TOPIC="dashboard";

            }


            if(System.getenv("GROUPID")!=null){

                InitConfigValues.GROUPID=System.getenv("GROUPID");
            }else{

                InitConfigValues.GROUPID="blockchain-dashboard";

            }

            if(System.getenv("WEBSOCKET_PORT")!=null){

                InitConfigValues.WEBSOCKET_PORT=Integer.parseInt(System.getenv("WEBSOCKET_PORT"));
            }else{

                InitConfigValues.WEBSOCKET_PORT=10000;

            }


            System.out.println("------------------------------------System Configuration------------------------------------------------\n\n");
            System.out.println("Kafka Host IP     :"+InitConfigValues.KAFKA_HOSTIP);
            System.out.println("Kafka Port        :"+InitConfigValues.KAFKA_PORT);
            System.out.println("Kafka Topic       :"+InitConfigValues.KAFKA_TOPIC);
            System.out.println("Kafka Group ID    :"+InitConfigValues.GROUPID);
            System.out.println("WebSocket Port    :"+InitConfigValues.WEBSOCKET_PORT+"\n\n");
            System.out.println("--------------------------------------------------------------------------------------------------------\n\n");



    }
}
