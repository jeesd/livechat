package org.mylivedata.app.dashboard.domain.custom;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lubo08 on 30.9.2014.
 */
public class DataTablesRequest implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5729357311325885884L;

	@JsonProperty(value = "draw")
    private int draw;

    @JsonProperty(value = "length")
    private int length;

    @JsonProperty(value = "start")
    private int start;

    @JsonProperty(value = "columns")
    private List<DataTablesColumn> columns;

    @JsonProperty(value = "order")
    private List<DataTablesOrder> order;

    @JsonProperty(value = "search")
    private DataTablesSearch search;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<DataTablesColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<DataTablesColumn> columns) {
        this.columns = columns;
    }

    public List<DataTablesOrder> getOrder() {
        return order;
    }

    public void setOrder(List<DataTablesOrder> order) {
        this.order = order;
    }

    public DataTablesSearch getSearch() {
        return search;
    }

    public void setSearch(DataTablesSearch search) {
        this.search = search;
    }
}
