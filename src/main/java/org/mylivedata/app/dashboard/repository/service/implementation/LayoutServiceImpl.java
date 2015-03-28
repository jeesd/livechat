package org.mylivedata.app.dashboard.repository.service.implementation;

import java.util.List;

import org.mylivedata.app.dashboard.domain.DefaultLayoutsEntity;
import org.mylivedata.app.dashboard.domain.MessageResourceEntity;
import org.mylivedata.app.dashboard.repository.DefaultLayoutsEntityRepository;
import org.mylivedata.app.dashboard.repository.MessageResourceEntityRepository;
import org.mylivedata.app.dashboard.repository.service.LayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lubo08 on 3.3.2015.
 */
@Service
public class LayoutServiceImpl implements LayoutService {

    @Autowired
    private DefaultLayoutsEntityRepository defaultLayoutsEntityRepository;
    @Autowired
    private MessageResourceEntityRepository messageResourceEntityRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=true, rollbackFor=Exception.class)
    public String getLayoutHtml(String name,String fragment) throws Exception {
        String layout = "";

        DefaultLayoutsEntity defaultLayoutsEntity = defaultLayoutsEntityRepository.findByNameAndFragment("bubble_style","bubble");
        layout = defaultLayoutsEntity.getHtml();
        return layout;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=true, rollbackFor=Exception.class)
    public String getLayoutCss(String name,String fragment) throws Exception {
        String layout = "";

        DefaultLayoutsEntity defaultLayoutsEntity = defaultLayoutsEntityRepository.findByNameAndFragment("bubble_style","bubble");
        layout = defaultLayoutsEntity.getCss();
        return layout;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=true, rollbackFor=Exception.class)
    public List<MessageResourceEntity> getMessageResource() throws Exception {
        return messageResourceEntityRepository.findAll();
    }

}
