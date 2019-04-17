package extractors;

import core.PageExtractor;
import core.TargetUrl;
import model.RestaurantInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;
import utils.MyUtils;

import java.util.LinkedList;
import java.util.List;

@TargetUrl("http://travel.qunar.com/(.*)-meishi(.*)")
public class RestaurantExtractor implements PageExtractor {

    @Override
    public void extractInfo(Page page) {
        List<RestaurantInfo> restaurantInfos = new LinkedList<>();
        Selectable html = page.getHtml();

        List<Selectable> items = html.$("div.b_food_new").$("li.item").nodes();
        for (Selectable item : items) {
            RestaurantInfo restaurantInfo = new RestaurantInfo();
            restaurantInfo.restaurantName = item.$("span.cn_tit", "text").get();
            restaurantInfo.restaurantId = MyUtils.extractId(item.$("a.titlink").links().get());
            restaurantInfo.score = item.$("span.cur_score", "text").get();
            restaurantInfo.distance = item.$("div.distance").$("span").nodes().get(1).$("span", "text").get();

            restaurantInfos.add(restaurantInfo);
        }

        restaurantInfos.forEach(System.out::println);
    }
}
