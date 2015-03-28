package org.mylivedata.app.dashboard.domain.custom;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lubo08 on 30.9.2014.
 */
public class DataTablesResponse <T> {

    @JsonProperty(value = "draw")
    private int draw;

    @JsonProperty(value = "recordsTotal")
    private long recordsTotal;

    @JsonProperty(value = "recordsFiltered")
    private long recordsFiltered;

    @JsonProperty(value = "data")
    private List<T> data = new ArrayList<T>();

    @JsonProperty(value = "error")
    private String error;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
