package com.blockchain.kafkabroadcaster.main;

import com.blockchain.kafkabroadcaster.server.WebsocketServer;
 import org.elasticsearch.action.TransportActions;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;

import org.elasticsearch.client.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.yaml.snakeyaml.constructor.Construct;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.json.JSONObject;

import javax.jws.WebService;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;


public class TestMain {
    public static void main(String[]ar)throws Exception{


//        WebsocketServer wss=new WebsocketServer();
//        wss.start();
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        int i=0;
//
//        while (true) {
//            i++;
//            wss.broadCastMessage("Wow this is from web >"+i);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        Settings settings = ImmutableSettings.settingsBuilder().put("client.transport.sniff", true).put("cluster.name", "docker-cluster").build();

        //Specify the IP address of Elasticsearch Master Node
        Client esclient = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9200));

        //Sample Query
        String queryString = "{\"query\":{\"query_string\":{\"query\":\"field:value\"}},\"fields\": [\"fieldname\"]}";

        //Sample Query - JSONObject
        //We convert the raw query string to JSONObject to avoid query parser error in Elasticsearch
        JSONObject queryStringObject = new JSONObject(queryString);

        //Elasticsearch Response
        SearchResponse response = esclient.prepareSearch("indexName").setTypes("typeName").setSource(queryStringObject.toString()).execute().actionGet();

        //Elasticsearch Response Hits
        SearchHits hits = response.getHits();

        //Iterate SearchHits Object to get the Documents
        //i.e. For each document
        for (int i = 0; i < hits.totalHits(); i++) {

            //Fields Object for each document
            Map<String, SearchHitField> responseFields = hits.getAt(i).getFields();

            //Access required field
            SearchHitField field = responseFields.get("fieldname");

            //Print field value
            //field.getValue() return single value
            //field.getValues()returns the value in an array
            System.out.println(field.getValues());
        }

        //Close Transport Client Connection
        esclient.close();
      }
}
