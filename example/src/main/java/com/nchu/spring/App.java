package com.nchu.spring;

import com.nchu.spring.bean.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Decription Spring
 * @Author yangsj
 * @Date 2020/6/27 12:58 下午
 **/
public class App {

	public static void main(String[] args) {
		testGetBean();
	}

	/**
	 * @Description  从Spring容器中获取bean
	 * @Author yangsj
	 * @Date 2020/6/27 6:20 下午
	 **/
	public static void testGetBean(){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-test.xml");
		User user = (User)context.getBean("user");
		System.out.println(user);
	}
}
