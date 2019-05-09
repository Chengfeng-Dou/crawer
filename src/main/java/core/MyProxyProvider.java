package core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;
import utils.IpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyProxyProvider implements ProxyProvider {

    private long counter = 0;
    private List<Proxy> proxyList = new ArrayList<>();


    @Override
    public void returnProxy(Proxy proxy, Page page, Task task) {

    }

    @Override
    public synchronized Proxy getProxy(Task task) {
        if (counter % 2000 == 0) {
            String ipStr = IpUtils.getNewIp();
            System.out.println(ipStr);
            proxyList.clear();

            while (ipStr == null) {
                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                    ipStr = IpUtils.getNewIp();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            JSONObject object = JSON.parseObject(ipStr);
            JSONArray array = object.getJSONArray("data");
            for (int i = 0; i < array.size(); i++) {
                JSONObject ipObject = array.getJSONObject(i);
                String ip = ipObject.getString("ip");
                String port = ipObject.getString("port");
                System.out.println(ip + ":" + port);
                proxyList.add(new Proxy(ip, Integer.parseInt(port)));
            }
        }

        counter++;
        return proxyList.get((int) (counter % 4));
    }


}
