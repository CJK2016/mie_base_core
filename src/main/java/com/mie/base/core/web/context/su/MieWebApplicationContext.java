package com.mie.base.core.web.context.su;


import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.web.context.support.XmlWebApplicationContext;
import java.io.IOException;

public class MieWebApplicationContext extends XmlWebApplicationContext{

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
        // super.loadBeanDefinitions(beanFactory);
        // Create a new XmlBeanDefinitionReader for the given BeanFactory.
        MieBeanDefinitionReader beanDefinitionReader = new MieBeanDefinitionReader(beanFactory);

        // Configure the bean definition reader with this context's
        // resource loading environment.
        beanDefinitionReader.setEnvironment(getEnvironment());
        beanDefinitionReader.setResourceLoader(this);
        beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));

        // Allow a subclass to provide custom initialization of the reader,
        // then proceed with actually loading the bean definitions.
        initBeanDefinitionReader(beanDefinitionReader);
        loadBeanDefinitions(beanDefinitionReader);
    }
}
