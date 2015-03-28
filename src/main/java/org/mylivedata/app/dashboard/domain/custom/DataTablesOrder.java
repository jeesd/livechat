package org.mylivedata.app.dashboard.domain.custom;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lubo08 on 3.10.2014.
 */
public class DataTablesOrder {

    @JsonProperty(value = "column")
    private int column;

    @JsonProperty(value = "dir")
    private String dir;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
