package org.mylivedata.app.dashboard.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lubo08 on 3.3.2015.
 */
@Entity
@Table(name = "default_layouts", schema = "", catalog = "mylivedata")
public class DefaultLayoutsEntity {
    private Integer layoutId;
    private String html;
    private String css;
    private String name;
    private String fragment;
    private String description;

    @Id
    @Column(name = "layout_id", nullable = false, insertable = true, updatable = true)
    public Integer getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Integer layoutId) {
        this.layoutId = layoutId;
    }

    @Basic
    @Column(name = "html", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Basic
    @Column(name = "css", nullable = true, insertable = true, updatable = true, length = 65535)
    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "fragment", nullable = true, insertable = true, updatable = true, length = 50)
    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    @Basic
    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultLayoutsEntity that = (DefaultLayoutsEntity) o;

        if (css != null ? !css.equals(that.css) : that.css != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (fragment != null ? !fragment.equals(that.fragment) : that.fragment != null) return false;
        if (html != null ? !html.equals(that.html) : that.html != null) return false;
        if (layoutId != null ? !layoutId.equals(that.layoutId) : that.layoutId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = layoutId != null ? layoutId.hashCode() : 0;
        result = 31 * result + (html != null ? html.hashCode() : 0);
        result = 31 * result + (css != null ? css.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (fragment != null ? fragment.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
