package org.mylivedata.app.dashboard.repository.service;


import java.util.List;

import org.mylivedata.app.dashboard.domain.MessageResourceEntity;

/**
 * Created by lubo08 on 3.3.2015.
 */
public interface LayoutService {

    public String getLayoutHtml(String name,String fragment) throws Exception;
    public String getLayoutCss(String name,String fragment) throws Exception;
    public List<MessageResourceEntity> getMessageResource() throws Exception;


}
