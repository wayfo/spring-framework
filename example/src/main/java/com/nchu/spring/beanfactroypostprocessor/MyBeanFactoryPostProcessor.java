package com.nchu.spring.beanfactroypostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

/**
 * @Decription 自定义 BeanFactroyPostProcessor
 * BeanFactoryPostProcessor 机制相当于给了我们在实例化Bean之前最后一次修改BeanDefinition 的机会
 * 我们可以利用这个机会对BeanDefinition做一些额外的操作，比如：更改某些Bean的一些属性，给某些Bean增加一些其他的一些信息等操作
 * @Author yangsj
 * @Date 2020/6/27 9:53 下午
 **/
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor , Ordered {
	public MyBeanFactoryPostProcessor() {
		System.out.println("BeanFactoryPostProcessor 实现类构造函数...");
	}
	/**
	 * @Description  该方法工作于 BeanDefinition 加载完成之后，bean实例化之前，其主要作用是对BeanDefinition进行修改
	 * @Author yangsj
	 * @Date 2020/6/27 9:58 下午
	 **/
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("调用自定义BeanFactroyPostProcessor...");
		System.out.println("容器中有BeanDefinition的个数："+beanFactory.getBeanDefinitionCount());
		//获取指定的BeanDefinition ,由bean id获取
		BeanDefinition bd = beanFactory.getBeanDefinition("user");
		//获取 Bean 的属性
		MutablePropertyValues pvs = bd.getPropertyValues();
		//更改Bean 的属性
		pvs.addPropertyValue("name","BeanFactroyPostProcessor");
		pvs.addPropertyValue("age",99);
	}

	/**
	 * @Description   排序，定义 BeanFactoryPostProcessor 的执行顺序
	 * @Author yangsj
	 * @Date 2020/6/27 10:09 下午
	 **/
	@Override
	public int getOrder() {
		return 1;
	}
}
