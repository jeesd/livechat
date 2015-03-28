package org.mylivedata.app.dashboard.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by lubo08 on 5.9.2014.
 */
@Entity
@Table(name = "user_geo_data", schema = "", catalog = "mylivedata")
public class UserGeoDataEntity {
    private Integer vgdId;
    private String countryCode;
    private String countryName;
    private String continentCode;
    private String continentName;
    private String timeZone;
    private String regionCode;
    private String regionName;
    private String owner;
    private String cityName;
    private String countyName;
    private String latitude;
    private String longitude;
    private Integer userId;
    private Integer sessionId;
    private UserSessionsEntity userSessionsBySessionId;
    private UsersEntity usersByUserId;

    @Id
    @Column(name = "vgd_id", nullable = false, insertable = true, updatable = true)
    public Integer getVgdId() {
        return vgdId;
    }

    public void setVgdId(Integer vgdId) {
        this.vgdId = vgdId;
    }

    @Basic
    @Column(name = "COUNTRY_CODE", nullable = true, insertable = true, updatable = true, length = 2)
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Basic
    @Column(name = "COUNTRY_NAME", nullable = true, insertable = true, updatable = true, length = 255)
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Basic
    @Column(name = "CONTINENT_CODE", nullable = true, insertable = true, updatable = true, length = 2)
    public String getContinentCode() {
        return continentCode;
    }

    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }

    @Basic
    @Column(name = "CONTINENT_NAME", nullable = true, insertable = true, updatable = true, length = 255)
    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    @Basic
    @Column(name = "TIME_ZONE", nullable = true, insertable = true, updatable = true, length = 20)
    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Basic
    @Column(name = "REGION_CODE", nullable = true, insertable = true, updatable = true, length = 2)
    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @Basic
    @Column(name = "REGION_NAME", nullable = true, insertable = true, updatable = true, length = 255)
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Basic
    @Column(name = "OWNER", nullable = true, insertable = true, updatable = true, length = 500)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "CITY_NAME", nullable = true, insertable = true, updatable = true, length = 255)
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Basic
    @Column(name = "COUNTY_NAME", nullable = true, insertable = true, updatable = true, length = 255)
    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    @Basic
    @Column(name = "LATITUDE", nullable = true, insertable = true, updatable = true, length = 20)
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "LONGITUDE", nullable = true, insertable = true, updatable = true, length = 20)
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserGeoDataEntity that = (UserGeoDataEntity) o;

        if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null) return false;
        if (continentCode != null ? !continentCode.equals(that.continentCode) : that.continentCode != null)
            return false;
        if (continentName != null ? !continentName.equals(that.continentName) : that.continentName != null)
            return false;
        if (countryCode != null ? !countryCode.equals(that.countryCode) : that.countryCode != null) return false;
        if (countryName != null ? !countryName.equals(that.countryName) : that.countryName != null) return false;
        if (countyName != null ? !countyName.equals(that.countyName) : that.countyName != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (regionCode != null ? !regionCode.equals(that.regionCode) : that.regionCode != null) return false;
        if (regionName != null ? !regionName.equals(that.regionName) : that.regionName != null) return false;
        if (timeZone != null ? !timeZone.equals(that.timeZone) : that.timeZone != null) return false;
        if (vgdId != null ? !vgdId.equals(that.vgdId) : that.vgdId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = vgdId != null ? vgdId.hashCode() : 0;
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (countryName != null ? countryName.hashCode() : 0);
        result = 31 * result + (continentCode != null ? continentCode.hashCode() : 0);
        result = 31 * result + (continentName != null ? continentName.hashCode() : 0);
        result = 31 * result + (timeZone != null ? timeZone.hashCode() : 0);
        result = 31 * result + (regionCode != null ? regionCode.hashCode() : 0);
        result = 31 * result + (regionName != null ? regionName.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (countyName != null ? countyName.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "session_id", nullable = false, insertable = true, updatable = true)
    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "session_id", nullable = false, insertable = false, updatable = false)
    public UserSessionsEntity getUserSessionsBySessionId() {
        return userSessionsBySessionId;
    }

    public void setUserSessionsBySessionId(UserSessionsEntity userSessionsBySessionId) {
        this.userSessionsBySessionId = userSessionsBySessionId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}
