package com.atguigu.es;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.es.pojo.Article;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @Author QZG
 * @create 2022/8/5 22:24
 */
public class Test1 {

    public static void main(String[] args) throws Exception {
        //初始化客户端
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //设置端口号和ip地址
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("content");

        SearchResponse response = client.prepareSearch("java0217")
                .setQuery(QueryBuilders.matchAllQuery())
                .setSize(50)
                .setFrom(0)
                .addSort("id", SortOrder.ASC)
                .get();
        Iterator<SearchHit> iterator = response.getHits().iterator();
        while (iterator.hasNext()){
            SearchHit next = iterator.next();
            System.out.println(next.getSourceAsString());
        }


        System.out.println(response.getHits().getTotalHits());
        //关闭连接
        client.close();

    }

    //查询所有数据
    private static void test2(TransportClient client) {
        SearchResponse response = client.prepareSearch("java0217")
                .setQuery(QueryBuilders.matchAllQuery())
                .get();
        SearchHits hits = response.getHits();
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            SearchHit next = iterator.next();
            Article article = JSONObject.parseObject(next.getSourceAsString(), Article.class);
            System.out.println(article);
        }
    }

    //文档下标查询（主键查询）
    private static void test1(TransportClient client) throws UnknownHostException {
        GetResponse response = client.prepareGet("java0217","article","1").get();
        System.out.println(response.getSourceAsString());
    }
}
