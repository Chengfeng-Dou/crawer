package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "restaurant")
@IdClass(ResturantId.class)
public class RestaurantInfo {
    @Id
    public String restaurantId;
    @Id
    public String siteId;
    public String restaurantName;
    public String distance;
    public String score;

    @Override
    public String toString() {
        return "RestaurantInfo{" +
                "restaurantId='" + restaurantId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", distance='" + distance + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
