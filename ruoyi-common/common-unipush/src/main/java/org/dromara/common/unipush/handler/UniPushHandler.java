package org.dromara.common.unipush.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dromara.common.unipush.entity.DataEntity;
import org.dromara.common.unipush.entity.UniPushResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class UniPushHandler{
    ObjectMapper objectMapper = new ObjectMapper();
    String url = "${UNIPUSH_PUSH_URL}";
    public String send(String cids, String title, String content) throws IOException {
        // 创建JSON数据
        ObjectMapper objectMapper = new ObjectMapper();
        DataEntity dataEntity = new DataEntity(cids, title, content);
        String jsonData = objectMapper.writeValueAsString(dataEntity);
        // 创建HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建HttpPost实例
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头，指定发送的数据类型为JSON
        httpPost.setHeader("Content-type", "application/json");

        // 设置请求体
        StringEntity entity = new StringEntity(jsonData, ContentType.APPLICATION_JSON.withCharset("UTF-8"));
        httpPost.setEntity(entity);

        // 发送请求并获取响应
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String result = EntityUtils.toString(responseEntity);
                ObjectMapper object = new ObjectMapper();
                UniPushResponse res = object.readValue(result, UniPushResponse.class);
                return res.getErrCode();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            response.close();
        }
        return "false";
    }
}
