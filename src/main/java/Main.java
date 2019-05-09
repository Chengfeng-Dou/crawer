import core.MyProxyProvider;
import core.MysqlPipeline;
import core.QunarPageDispatcher;
import extractors.SitePageExtractor;
import extractors.SitesPageExtractor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import utils.DataUtils;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        QunarPageDispatcher processor = new QunarPageDispatcher();
        processor.addExtractor(new SitesPageExtractor());
        processor.addExtractor(new SitePageExtractor());

        List<String> links = DataUtils.getCityList();
        String[] urls = new String[links.size()];
        urls = links.toArray(urls);
        for (int i = 0; i < urls.length; i++) {
            urls[i] = urls[i] + "-jingdian";
        }

        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(
                new MyProxyProvider()
        );

        Spider.create(processor)
                .addUrl(urls)
                .addPipeline(new MysqlPipeline())
                .setDownloader(httpClientDownloader)
                .setScheduler(new FileCacheQueueScheduler("/home/douchengfeng/urls"))
                .thread(4).run();

        DataUtils.close();
    }
}
