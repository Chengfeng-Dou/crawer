package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class QunarPageDispatcher implements PageProcessor {
    private List<Extractor> extractors = new ArrayList<>();
    private BASE64Encoder encoder = new BASE64Encoder();
    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(200)
            .setCharset("UTF-8")
            .addHeader("Proxy-Authorization", String.format("Basic %s", encoder.encode("1228853904@qq.com:Dcf141250029".getBytes())));

    private static final Logger logger = LoggerFactory.getLogger(QunarPageDispatcher.class);
    private static long accessTime = 0;

    //114.234.202.84:20881
    //180.108.194.44:26265
    //27.150.193.141:36791
    //115.195.72.63:42476
    //117.65.51.250:55391

    private String[] agent = {
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; AcooBrowser; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)",
            "Mozilla/4.0 (compatible; MSIE 7.0; AOL 9.5; AOLBuild 4337.35; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
            "Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 2.0.50727; Media Center PC 6.0)",
            "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)",
            "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 5.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.2; .NET CLR 3.0.04506.30)",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/523.15 (KHTML, like Gecko, Safari/419.3) Arora/0.3 (Change: 287 c9dfb30)",
            "Mozilla/5.0 (X11; U; Linux; en-US) AppleWebKit/527+ (KHTML, like Gecko, Safari/419.3) Arora/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.2pre) Gecko/20070215 K-Ninja/2.1.1",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9) Gecko/20080705 Firefox/3.0 Kapiko/3.0",
            "Mozilla/5.0 (X11; Linux i686; U;) Gecko/20070322 Kazehakase/0.4.5",
            "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.8) Gecko Fedora/1.9.0.8-1.fc10 Kazehakase/0.5.6",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.20 (KHTML, like Gecko) Chrome/19.0.1036.7 Safari/535.20",
            "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52",
    };

    public QunarPageDispatcher() {

    }

    /**
     * 根据 url 分派提取器
     *
     * @param page 页面
     */
    public void process(Page page) {
        String url = page.getUrl().get();
        for (Extractor extractor : extractors) {

            if (extractor.match(url)) {
                logger.info("find extractor!");
                extractor.process(page);
                break;
            }

        }

        setUsrAgent();
    }

    private void setUsrAgent() {
        accessTime++;
        if (accessTime % 10 != 0) {
            return;
        }
        int random = (int) (Math.random() * (agent.length - 1));
        site.setUserAgent(agent[random]);
    }

    public Site getSite() {
        return site;
    }

    public void addExtractor(PageExtractor pageExtractor) {
        Class<?> clazz = pageExtractor.getClass();
        TargetUrl targetUrl = clazz.getAnnotation(TargetUrl.class);
        Extractor extractor = new Extractor(targetUrl.value(), pageExtractor);
        extractors.add(extractor);
    }

    private static class Extractor {
        private String pattern;
        private PageExtractor pageExtractor;

        Extractor(String pattern, PageExtractor pageExtractor) {
            logger.info("build extractor, which matches " + pattern);
            this.pattern = pattern;
            this.pageExtractor = pageExtractor;
        }

        boolean match(String url) {
            return Pattern.matches(pattern, url);
        }

        void process(Page page) {
            pageExtractor.extractInfo(page);
        }
    }
}
