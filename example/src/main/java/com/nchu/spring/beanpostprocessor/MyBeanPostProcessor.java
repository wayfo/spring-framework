package com.nchu.spring.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @Decription 自定义 BeanPostProcessor
 * @Author yangsj
 * @Date 2020/6/27 5:58 下午
 **/
class MyBeanPostProcessor implements BeanPostProcessor {
	public MyBeanPostProcessor() {
		System.out.println("BeanPostProcessor 实现类构造函数...");
	}
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("前置处理-"+beanName+"开始初始化！");
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("后置处理-"+beanName+"初始化完成！");
		return bean;
	}

}
