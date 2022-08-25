package com.atguigu.es;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.es.pojo.Article;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author QZG
 * @create 2022/8/5 15:06
 */
public class TestEs {
    public static void main(String[] args) throws Exception {
        //初始化客户端
        TransportClient client = new  PreBuiltTransportClient(Settings.EMPTY);
        //设置端口号和ip地址
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));
        for (int i = 0; i < 100; i++) {
            Article article = new Article();
            article.setId(1L);
            article.setTitle("Elasticsearch 是位于 Elastic Stack 核心的分布式搜索和分析引擎");
            article.setContent("Elasticsearch 是位于 Elastic Stack 核心的分布式搜索和分析引擎。" +
                    "Logstash 和 Beats 有助于收集、聚合和丰富您的数据并将其存储在 Elasticsearch 中。" +
                    "Kibana 使您能够以交互方式探索、可视化和分享对数据的见解，并管理和监控堆栈。" +
                    "Elasticsearch 是索引、搜索和分析魔法发生的地方。");
            client.prepareIndex("java0217","article",i+"")
                    .setSource(JSONObject.toJSONString(article),XContentType.JSON)
                    .get();
        }

        //关闭连接
        client.close();
    }

    private static void extracted2() throws UnknownHostException {
        //初始化客户端
        TransportClient client = new  PreBuiltTransportClient(Settings.EMPTY);
        //设置端口号和ip地址
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Elasticsearch 是位于 Elastic Stack 核心的分布式搜索和分析引擎");
        article.setContent("Elasticsearch 是位于 Elastic Stack 核心的分布式搜索和分析引擎。" +
                "Logstash 和 Beats 有助于收集、聚合和丰富您的数据并将其存储在 Elasticsearch 中。" +
                "Kibana 使您能够以交互方式探索、可视化和分享对数据的见解，并管理和监控堆栈。" +
                "Elasticsearch 是索引、搜索和分析魔法发生的地方。");
        client.prepareIndex("java0217","article","1")
                .setSource(JSONObject.toJSONString(article),XContentType.JSON)
                .get();

        //关闭连接
        client.close();
    }

    private static void extracted1() throws IOException {

        //初始化客户端
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //设置地址和端口
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));
        //创建索引
        client.admin().indices().prepareCreate("java0217").get();
        //添加映射
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                    .startObject("article")
                        .startObject("properties")
                            .startObject("id")
                                .field("type","long")
                            .endObject()
                .startObject("title")
                .field("type","text")
                .field("analyzer","ik_max_word")
                .endObject()
                .startObject("content")
                .field("type","text")
                .field("analyzer","ik_max_word")
                .endObject()
                        .endObject()
                    .endObject()
                .endObject();
        //构建映射请求
        PutMappingRequest request = Requests.putMappingRequest("java0217").type("article").source(builder);
        //创建
        client.admin().indices().putMapping(request);
        //关闭客户端
        client.close();
    }

    private static void extracted() throws UnknownHostException {
        //初始化es客户端
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //设置es的ip和端口
        TransportAddress transportAddress = new TransportAddress(InetAddress.getByName("localhost"),9300);
        client.addTransportAddress(transportAddress);
        //创建索引
        client.admin().indices().prepareCreate("java0217").get();

        //关闭连接
        client.close();
    }


}
