package org.mylivedata.app.dashboard.domain.custom;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lubo08 on 3.10.2014.
 */
public class DataTablesSearch {

    @JsonProperty(value = "regex")
    private boolean regex;

    @JsonProperty(value = "value")
    private String value;

    public boolean isRegex() {
        return regex;
    }

    public void setRegex(boolean regex) {
        this.regex = regex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
