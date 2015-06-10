package org.mylivedata.app.configuration;

import org.cometd.annotation.ServerAnnotationProcessor;
import org.cometd.bayeux.server.BayeuxServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

/**
 * Created by lubo08 on 10.6.2015.
 */
@Component
public class Processor implements DestructionAwareBeanPostProcessor
{
    @Inject
    private BayeuxServer bayeuxServer;
    private ServerAnnotationProcessor processor;


    @PostConstruct
    private void init()
    {
        this.processor = new ServerAnnotationProcessor(bayeuxServer);
    }

    @PreDestroy
    private void destroy()
    {
    }

    public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException
    {
        processor.processDependencies(bean);
        processor.processConfigurations(bean);
        processor.processCallbacks(bean);
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String name) throws BeansException
    {
        return bean;
    }

    public void postProcessBeforeDestruction(Object bean, String name) throws BeansException
    {
        processor.deprocessCallbacks(bean);
    }
}
