package core;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;
import utils.IpUtils;

import java.util.concurrent.TimeUnit;

public class MyProxyProvider implements ProxyProvider {

    private int counter = 0;
    private Proxy current;


    @Override
    public void returnProxy(Proxy proxy, Page page, Task task) {

    }

    @Override
    public synchronized Proxy getProxy(Task task) {
        if (counter % 15 == 0) {
            String ipStr = IpUtils.getNewIp();
            System.out.println(ipStr);
            while (ipStr == null) {
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                    ipStr = IpUtils.getNewIp();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int pos = ipStr.indexOf(":");

            String ip = ipStr.substring(0, pos);
            String port = ipStr.substring(pos + 1).replaceAll("\n", "");

            System.out.println(ip + ":" + port);
            current = new Proxy(ip, Integer.parseInt(port));
        }

        counter++;
        return current;
    }


}
