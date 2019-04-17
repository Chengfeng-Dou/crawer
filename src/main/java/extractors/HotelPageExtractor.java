package extractors;

import core.PageExtractor;
import core.TargetUrl;
import model.HotelInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;
import utils.MyUtils;

import java.util.LinkedList;
import java.util.List;

@TargetUrl("http://travel.qunar.com/(.*)-jiudian(.*)")
public class HotelPageExtractor implements PageExtractor {

    @Override
    public void extractInfo(Page page) {
        List<HotelInfo> hotels = new LinkedList<>();
        Selectable html = page.getHtml();

        List<Selectable> items = html.$("div.b_hotel_new").$("li.item").nodes();
        for (Selectable item : items) {
            HotelInfo hotelInfo = new HotelInfo();
            hotelInfo.hotelName = item.$("a.cn_tit", "text").get();
            hotelInfo.hotelId = MyUtils.extractId(item.$("a.cn_tit").links().get());
            hotelInfo.score = item.$("span.sum_bold", "text").get();
            hotelInfo.distance = item.$("div.distance").$("span").nodes().get(1).$("span", "text").get();

            hotels.add(hotelInfo);
        }

        hotels.forEach(System.out::println);
    }
}
