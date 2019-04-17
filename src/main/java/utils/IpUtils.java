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
//        try {
//            TimeUnit.MINUTES.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        HttpGet getIp = new HttpGet("http://api.ip.data5u.com/dynamic/get.html?order=e8eee3b10766384f80477b5f983a3481");
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
