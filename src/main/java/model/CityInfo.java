package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city")
public class CityInfo {
    @Id
    @Column(name = "cityId")
    public String cityId;
    @Column(name = "cityName")
    public String cityName;
    @Column(name = "province")
    public String province;
    @Column(name = "area")
    public String area;
    @Column(name = "link")
    public String link;

    @Override
    public String toString() {
        return "CityInfo{" +
                "cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", province='" + province + '\'' +
                ", area='" + area + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
