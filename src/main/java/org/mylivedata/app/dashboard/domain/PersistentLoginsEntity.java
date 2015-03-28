package org.mylivedata.app.dashboard.domain;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lubo08 on 5.4.2014.
 */
@Entity
@Table(name = "persistent_logins", schema = "", catalog = "mylivedata")
public class PersistentLoginsEntity {
    private String username;
    private String series;
    private String token;
    private Timestamp lastUsed;

    @Basic
    @Column(name = "username", nullable = false, insertable = true, updatable = true, length = 64, precision = 0)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Id
    @Column(name = "series", nullable = false, insertable = true, updatable = true, length = 64, precision = 0)
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    @Basic
    @Column(name = "token", nullable = false, insertable = true, updatable = true, length = 64, precision = 0)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "last_used", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Timestamp getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Timestamp lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersistentLoginsEntity that = (PersistentLoginsEntity) o;

        if (lastUsed != null ? !lastUsed.equals(that.lastUsed) : that.lastUsed != null) return false;
        if (series != null ? !series.equals(that.series) : that.series != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (series != null ? series.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (lastUsed != null ? lastUsed.hashCode() : 0);
        return result;
    }
}
