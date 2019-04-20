package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "shop")
@IdClass(ShopId.class)
public class ShopInfo {
    @Id
    public String shopId;
    @Id
    public String siteId;
    public String shopName;
    public String score;
    public String distance;

    @Override
    public String toString() {
        return "ShopInfo{" +
                "shopId='" + shopId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", score='" + score + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
