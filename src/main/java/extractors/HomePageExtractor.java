package extractors;


import core.PageExtractor;
import core.TargetUrl;
import model.CityInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;
import utils.MyUtils;

import java.util.LinkedList;
import java.util.List;

@TargetUrl(value = "http://travel.qunar.com/place/.*?")
public class HomePageExtractor implements PageExtractor {

    @Override
    public void extractInfo(Page page) {
        Selectable root = page.getHtml().xpath("//div[@class='contbox current']");
        Selectable areas = root.css("dl");
        List<CityInfo> cityList = new LinkedList<>();


        for (Selectable area : areas.nodes()) {

            String areaName = area.$("dt", "text").get();
            Selectable provinces = area.css("div.sub_list");

            for (Selectable province : provinces.nodes()) {

                String provinceName = province.$("span.tit", "text").get();
                if (provinceName != null) {
                    provinceName = provinceName.replaceAll("(    )|：", "");
                }

                Selectable cities = province.css("a");

                for (Selectable city : cities.nodes()) {

                    String link = city.links().get();
                    String cityName = city.$("a", "text").get();

                    CityInfo cityInfo = new CityInfo();
                    cityInfo.area = areaName;
                    cityInfo.province = provinceName == null ? cityName : provinceName;
                    cityInfo.link = link;
                    cityInfo.cityName = cityName;
                    cityInfo.cityId = MyUtils.extractCityCode(link);

                    cityList.add(cityInfo);
                    System.out.println(cityInfo.toString());
                }
            }
        }

        page.putField("list", cityList);

    }


}
