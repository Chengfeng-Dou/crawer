package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class NearSiteKey implements Serializable {
    @Column
    public String nearSiteId;
    @Column
    public String siteId;
}
