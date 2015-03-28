package org.mylivedata.app.util;

import java.util.ArrayList;
import java.util.List;

import org.mylivedata.app.dashboard.domain.custom.DataTablesOrder;
import org.mylivedata.app.dashboard.domain.custom.DataTablesRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Created by lubo08 on 19.10.2014.
 * Convert jquery DataTables search and order to spring data paging.
 */
public class DataTablesUtils {

    /**
     * Return Spring Data Sort from jquery DataTables request
     * @param request
     * @return
     */
    public static Sort getSort(DataTablesRequest request){
        Sort sort = null;
        List<Sort.Order> orderData = new ArrayList<>();

        for(DataTablesOrder order: request.getOrder()){
            orderData.add(
                    new Sort.Order(
                            order.getDir().equals("asc")?Sort.Direction.ASC:Sort.Direction.DESC,
                            request.getColumns().get(order.getColumn()).getData()
                    )
            );
        }
        if(!orderData.isEmpty())
            sort = new Sort(orderData);

        return sort;
    }

    /**
     * Return zero-based page position from jquery DataTables request.
     * @param request
     * @return
     */
    public static int getPage(DataTablesRequest request){
        int pageNumber = 0;

        pageNumber = request.getStart()/request.getLength();

        return pageNumber;
    }

    /**
     * Return Spring Data page request from jquery data tables request.
     * @param request
     * @return
     */
    public static PageRequest getPageRequest(DataTablesRequest request){
        PageRequest pageRequest = new PageRequest(
                DataTablesUtils.getPage(request),
                request.getLength(),
                DataTablesUtils.getSort(request)
        );
        return pageRequest;
    }
}
