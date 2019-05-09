package utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class IpUtils {
    private static CloseableHttpClient client = HttpClients.createDefault();

    public static String getNewIp() {
        System.out.println("request Ip");
        HttpGet getIp = new HttpGet("http://api.wandoudl.com/api/ip?app_key=3d2b3b9c02034db08a7567ba0ebd1f95&pack=204679&port=4&num=20&xy=1&type=2");
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        getIp.setConfig(requestConfig);
        try {
            HttpResponse response = client.execute(getIp);
            return EntityUtils.toString(response.getEntity());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println(getNewIp());
    }
}
