package extractors;

import core.PageExtractor;
import core.TargetUrl;
import us.codecraft.webmagic.Page;

import java.util.List;

@TargetUrl("http://travel.qunar.com/[a-z]-[a-z]*[0-9]*-[a-z]+-jingdian(.*)")
public class SitesPageExtractor implements PageExtractor {

    @Override
    public void extractInfo(Page page) {
        List<String> sites = page.getHtml().$("div.ct").links().all();
        String next = page.getHtml().xpath("//a[@class='page next']").links().get();

        sites.forEach(System.out::println);
        page.addTargetRequests(sites);
        System.out.println(next);
        if(next != null){
            page.addTargetRequest(next);
        }

    }
}
