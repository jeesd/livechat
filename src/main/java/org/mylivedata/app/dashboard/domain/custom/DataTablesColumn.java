package org.mylivedata.app.dashboard.domain.custom;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lubo08 on 3.10.2014.
 */
public class DataTablesColumn {

    @JsonProperty(value = "data")
    private String data;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "searchable")
    private boolean searchable;

    @JsonProperty(value = "orderable")
    private boolean orderable;

    @JsonProperty(value = "search")
    private DataTablesSearch search;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public boolean isOrderable() {
        return orderable;
    }

    public void setOrderable(boolean orderable) {
        this.orderable = orderable;
    }

    public DataTablesSearch getSearch() {
        return search;
    }

    public void setSearch(DataTablesSearch search) {
        this.search = search;
    }
}
