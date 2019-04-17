package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ShopId implements Serializable {
    @Column
    public String shopId;
    @Column
    public String siteId;
}
