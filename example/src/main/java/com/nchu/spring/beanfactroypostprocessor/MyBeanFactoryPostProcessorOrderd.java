package com.nchu.spring.beanfactroypostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

/**
 * @Decription BeanFactoryPostProcessor 支持排序
 * 一个容器可以拥有多个 BeanFactoryPostProcessor ，我们可以实现 Ordered 接口，定义每个 BeanFactoryPostProcessor 的执行顺序
 * @Author yangsj
 * @Date 2020/6/27 10:05 下午
 **/
public class MyBeanFactoryPostProcessorOrderd implements BeanFactoryPostProcessor , Ordered {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("调用自定义BeanFactroyPostProcessor...");
		System.out.println("容器中有BeanDefinition的个数："+beanFactory.getBeanDefinitionCount());
		//获取指定的BeanDefinition ,由bean id获取
		BeanDefinition bd = beanFactory.getBeanDefinition("user");
		//获取 Bean 的属性
		MutablePropertyValues pvs = bd.getPropertyValues();
		//更改Bean 的属性
		pvs.addPropertyValue("name","Ordered");
	}

	/**
	 * @Description  排序，定义 BeanFactoryPostProcessor 的执行顺序
	 * @Author yangsj
	 * @Date 2020/6/27 10:09 下午
	 **/
	@Override
	public int getOrder() {
		return 2;
	}
}
