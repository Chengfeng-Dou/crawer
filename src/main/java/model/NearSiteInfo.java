package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "near_site")
@IdClass(NearSiteKey.class)
public class NearSiteInfo {
    @Id
    public String nearSiteId;
    @Id
    public String siteId;
    public String siteName;
    public String distance;
    public String score;


    @Override
    public String toString() {
        return "NearSiteInfo{" +
                "nearSiteId='" + nearSiteId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", distance='" + distance + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
