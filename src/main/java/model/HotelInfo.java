package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "hotel")
@IdClass(HotelKey.class)
public class HotelInfo {
    @Id
    public String hotelId;
    @Id
    public String siteId;
    public String hotelName;
    public String distance;
    public String score;

    @Override
    public String toString() {
        return "HotelInfo{" +
                "hotelId='" + hotelId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", distance='" + distance + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
