package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class HotelKey implements Serializable {
    @Column
    public String hotelId;
    @Column
    public String siteId;
}
