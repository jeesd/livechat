package org.mylivedata.app.dashboard.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lubo08 on 4.3.2015.
 */
@Entity
@Table(name = "message_resource", schema = "", catalog = "mylivedata")
public class MessageResourceEntity {
    private Integer id;
    private String langIsoCode;
    private String textCode;
    private String textValue;
    private String coutryIsoCode;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "lang_iso_code", nullable = true, insertable = true, updatable = true, length = 2)
    public String getLangIsoCode() {
        return langIsoCode;
    }

    public void setLangIsoCode(String langIsoCode) {
        this.langIsoCode = langIsoCode;
    }

    @Basic
    @Column(name = "text_code", nullable = true, insertable = true, updatable = true, length = 100)
    public String getTextCode() {
        return textCode;
    }

    public void setTextCode(String textCode) {
        this.textCode = textCode;
    }

    @Basic
    @Column(name = "text_value", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    @Basic
    @Column(name = "coutry_iso_code", nullable = true, insertable = true, updatable = true, length = 2)
    public String getCoutryIsoCode() {
        return coutryIsoCode;
    }

    public void setCoutryIsoCode(String coutryIsoCode) {
        this.coutryIsoCode = coutryIsoCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageResourceEntity that = (MessageResourceEntity) o;

        if (coutryIsoCode != null ? !coutryIsoCode.equals(that.coutryIsoCode) : that.coutryIsoCode != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (langIsoCode != null ? !langIsoCode.equals(that.langIsoCode) : that.langIsoCode != null) return false;
        if (textCode != null ? !textCode.equals(that.textCode) : that.textCode != null) return false;
        if (textValue != null ? !textValue.equals(that.textValue) : that.textValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (langIsoCode != null ? langIsoCode.hashCode() : 0);
        result = 31 * result + (textCode != null ? textCode.hashCode() : 0);
        result = 31 * result + (textValue != null ? textValue.hashCode() : 0);
        result = 31 * result + (coutryIsoCode != null ? coutryIsoCode.hashCode() : 0);
        return result;
    }
}
