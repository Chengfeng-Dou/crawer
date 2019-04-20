package extractors;

import core.PageExtractor;
import core.TargetUrl;
import model.*;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;
import utils.MyUtils;

import java.util.LinkedList;
import java.util.List;

@TargetUrl("http://travel.qunar.com/p-oi[0-9]+-[a-z]+")
public class SitePageExtractor implements PageExtractor {

    @Override
    public void extractInfo(Page page) {
        Selectable html = page.getHtml();
        SiteInfo info = new SiteInfo();
        info.siteName = html.$("div.e_crumbs").$("strong", "text").get();
        info.score = html.$("span.cur_score", "text").get();
        info.siteRank = html.$("span.sum", "text").get();
        info.suggested = html.$("div.time", "text").get();
        info.siteId = MyUtils.extractId(page.getUrl().get());
        info.cityId = MyUtils.extractCityCode(html.$("div.ranking").links().get());
        info.siteDes = html.$("div#gs").$("p", "text").get();

        List<Selectable> telAndAddress = html.$("td.td_l").$("span").nodes();

        try {
            info.address = telAndAddress.get(0).$("span", "text").get();
            info.tel = telAndAddress.get(1).$("span", "text").get();
        } catch (Exception ignored) {
        }


        info.openingHour = html.$("td.td_r").$("p", "text").get();
        info.ticketPrice = html.xpath("//div[@class='e_db_content_box e_db_content_dont_indent']/p/text()").get();
        info.tourismSeason = html.xpath("//div[@class='b_detail_section b_detail_travelseason']/div/p/text()").get();
        info.trafficSuggest = html.$("#jtzn").xpath("//div[@class='b_detail_section b_detail_traffic']").toString();

        Selectable comment = html.xpath("//div[@class='b_detail_section b_detail_comment']");
        info.commentsNum = comment.$("span.num", "text").get();
        if (info.commentsNum != null) {
            info.commentsNum = info.commentsNum.replaceAll("\\(", "").replaceAll("\\)", "");
        }


        page.putField("object", info);

        System.out.println(info);

        List<Selectable> iconBoxUls = html.$("div#idContBox").$("ul").nodes();

        if (iconBoxUls == null || iconBoxUls.size() < 1) return;
        Selectable nearSite = iconBoxUls.get(0);
        List<NearSiteInfo> nearSiteInfos = new LinkedList<>();
        for (Selectable item : nearSite.$("li.item").nodes()) {
            NearSiteInfo siteInfo = new NearSiteInfo();
            siteInfo.nearSiteId = MyUtils.extractId(item.links().get());
            siteInfo.siteId = info.siteId;
            siteInfo.siteName = item.$("a.tit", "text").get();

            if (siteInfo.siteName == null) {
                System.exit(1);
            }

            siteInfo.score = String.valueOf(MyUtils.getScore(item.$("span.cur_star", "style").get()));
            siteInfo.distance = item.$("span.distance", "text").get();
            nearSiteInfos.add(siteInfo);
            System.out.println(siteInfo);
        }

        page.putField("near-site-list", nearSiteInfos);

        if (iconBoxUls.size() < 2) return;
        Selectable nearRestaurant = iconBoxUls.get(1);
        List<RestaurantInfo> restaurantInfos = new LinkedList<>();
        for (Selectable item : nearRestaurant.$("li.item").nodes()) {
            RestaurantInfo restaurantInfo = new RestaurantInfo();
            restaurantInfo.restaurantId = MyUtils.extractId(item.links().get());
            restaurantInfo.siteId = info.siteId;
            restaurantInfo.restaurantName = item.$("a.tit", "text").get();
            restaurantInfo.score = String.valueOf(MyUtils.getScore(item.$("span.cur_star", "style").get()));
            restaurantInfo.distance = item.$("span.distance", "text").get();
            restaurantInfos.add(restaurantInfo);
            System.out.println(restaurantInfo);
        }

        page.putField("near-rest-list", restaurantInfos);

        if (iconBoxUls.size() < 3) return;
        Selectable nearHotel = iconBoxUls.get(2);
        List<HotelInfo> hotelInfos = new LinkedList<>();
        for (Selectable item : nearHotel.$("li.item").nodes()) {
            HotelInfo hotelInfo = new HotelInfo();
            hotelInfo.hotelId = MyUtils.extractId(item.links().get());
            hotelInfo.siteId = info.siteId;
            hotelInfo.hotelName = item.$("a.tit", "text").get();
            hotelInfo.score = String.valueOf(MyUtils.getScore(item.$("span.cur_star", "style").get()));
            hotelInfo.distance = item.$("span.distance", "text").get();
            hotelInfos.add(hotelInfo);
            System.out.println(hotelInfo);
        }

        page.putField("near-hotel-list", hotelInfos);

        if (iconBoxUls.size() < 4) return;
        Selectable nearShop = iconBoxUls.get(3);
        List<ShopInfo> shopInfos = new LinkedList<>();
        for (Selectable item : nearShop.$("li.item").nodes()) {
            ShopInfo shopInfo = new ShopInfo();
            shopInfo.shopId = MyUtils.extractId(item.links().get());
            shopInfo.siteId = info.siteId;
            shopInfo.shopName = item.$("a.tit", "text").get();
            shopInfo.score = String.valueOf(MyUtils.getScore(item.$("span.cur_star", "style").get()));
            shopInfo.distance = item.$("span.distance", "text").get();
            shopInfos.add(shopInfo);
            System.out.println(shopInfo);
        }

        page.putField("near-shop-list", shopInfos);

    }
}
