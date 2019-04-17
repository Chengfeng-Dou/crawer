package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "site")
public class SiteInfo {
    @Id
    public String siteId;
    public String siteName;
    public String cityId;
    public String siteRank;
    @Column(columnDefinition = "longtext")
    public String siteDes;
    @Column(columnDefinition = "longtext")
    public String suggested;
    public String score;
    @Column(columnDefinition = "longtext")
    public String address;
    @Column(columnDefinition = "longtext")
    public String tel;
    @Column(columnDefinition = "longtext")
    public String openingHour;
    @Column(columnDefinition = "longtext")
    public String ticketPrice;
    @Column(columnDefinition = "longtext")
    public String tourismSeason;
    @Column(columnDefinition = "longtext")
    public String trafficSuggest;
    public String commentsNum;

    @Override
    public String toString() {
        return "SiteInfo{\n" +
                "siteId='" + siteId + '\'' +
                ", \nsiteName='" + siteName + '\'' +
                ", \ncityId='" + cityId + '\'' +
                ", \nsiteRank='" + siteRank + '\'' +
                ", \nsiteDes='" + siteDes + '\'' +
                ", \nsuggested='" + suggested + '\'' +
                ", \nscore='" + score + '\'' +
                ", \naddress='" + address + '\'' +
                ", \ntel='" + tel + '\'' +
                ", \nopeningHour='" + openingHour + '\'' +
                ", \nticketPrice='" + ticketPrice + '\'' +
                ", \ntourismSeason='" + tourismSeason + '\'' +
                ", \ntrafficSuggest='" + trafficSuggest + '\'' +
                ", \ncommentsNum='" + commentsNum + '\'' +
                '}';
    }
}
