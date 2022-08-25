package com.atguigu.es;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.es.pojo.Article;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * @Author QZG
 * @create 2022/8/5 17:32
 */
public class Test {
    public static void main(String[] args) {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("标题1");
        article.setContent("内容1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",article.getId());
        jsonObject.put("title",article.getTitle());
        jsonObject.put("content",article.getContent());
        System.out.println("json"+JSONObject.toJSON(article));
        System.out.println("jsonObject"+jsonObject);
        System.out.println("jsonString"+JSONObject.toJSONString(article));
    }
}
