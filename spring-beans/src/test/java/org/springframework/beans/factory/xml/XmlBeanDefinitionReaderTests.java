/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.xml;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.xml.sax.InputSource;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.tests.sample.beans.TestBean;
import org.springframework.util.ObjectUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Rick Evans
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
public class XmlBeanDefinitionReaderTests {

	@Test
	public void setParserClassSunnyDay() {
		SimpleBeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		new XmlBeanDefinitionReader(registry).setDocumentReaderClass(DefaultBeanDefinitionDocumentReader.class);
	}

	@Test
	public void withOpenInputStream() {
		SimpleBeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		Resource resource = new InputStreamResource(getClass().getResourceAsStream("test.xml"));
		assertThatExceptionOfType(BeanDefinitionStoreException.class).isThrownBy(() ->
				new XmlBeanDefinitionReader(registry).loadBeanDefinitions(resource));
	}

	@Test
	public void withOpenInputStreamAndExplicitValidationMode() {
		SimpleBeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		Resource resource = new InputStreamResource(getClass().getResourceAsStream("test.xml"));
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
		reader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_DTD);
		reader.loadBeanDefinitions(resource);
		testBeanDefinitions(registry);
	}

	@Test
	public void withImport() {
		SimpleBeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		Resource resource = new ClassPathResource("import.xml", getClass());
		new XmlBeanDefinitionReader(registry).loadBeanDefinitions(resource);
		testBeanDefinitions(registry);
	}

	@Test
	public void withWildcardImport() {
		SimpleBeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		Resource resource = new ClassPathResource("importPattern.xml", getClass());
		new XmlBeanDefinitionReader(registry).loadBeanDefinitions(resource);
		testBeanDefinitions(registry);
	}

	@Test
	public void withInputSource() {
		SimpleBeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		InputSource resource = new InputSource(getClass().getResourceAsStream("test.xml"));
		assertThatExceptionOfType(BeanDefinitionStoreException.class).isThrownBy(() ->
				new XmlBeanDefinitionReader(registry).loadBeanDefinitions(resource));
	}

	@Test
	public void withInputSourceAndExplicitValidationMode() {
		SimpleBeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		InputSource resource = new InputSource(getClass().getResourceAsStream("test.xml"));
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
		reader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_DTD);
		reader.loadBeanDefinitions(resource);
		testBeanDefinitions(registry);
	}
	/**
	 * @Description  解析XML配置文件成对应的BeanDefinition单元测试
	 * XML Resource => XML Document => Bean Definition
	 * @Author yangsj
	 * @Date 2019-11-10 17:11
	 **/
	@Test
	public void withFreshInputStream() {
		//获取 BeanDefinitionRegistry
		SimpleBeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		//获取资源
		Resource resource = new ClassPathResource("import.xml", getClass());
		//根据 BeanDefinitionRegistry 创建一个对象解析器
		new XmlBeanDefinitionReader(registry).loadBeanDefinitions(resource);
		testBeanDefinitions(registry);
	}

	private void testBeanDefinitions(BeanDefinitionRegistry registry) {
		assertThat(registry.getBeanDefinitionCount()).isEqualTo(24);
		assertThat(registry.getBeanDefinitionNames().length).isEqualTo(24);
		assertThat(Arrays.asList(registry.getBeanDefinitionNames()).contains("rod")).isTrue();
		assertThat(Arrays.asList(registry.getBeanDefinitionNames()).contains("aliased")).isTrue();
		assertThat(registry.containsBeanDefinition("rod")).isTrue();
		assertThat(registry.containsBeanDefinition("aliased")).isTrue();
		assertThat(registry.getBeanDefinition("rod").getBeanClassName()).isEqualTo(TestBean.class.getName());
		assertThat(registry.getBeanDefinition("aliased").getBeanClassName()).isEqualTo(TestBean.class.getName());
		assertThat(registry.isAlias("youralias")).isTrue();
		String[] aliases = registry.getAliases("aliased");
		assertThat(aliases.length).isEqualTo(2);
		assertThat(ObjectUtils.containsElement(aliases, "myalias")).isTrue();
		assertThat(ObjectUtils.containsElement(aliases, "youralias")).isTrue();
	}
	/**
	 * @Description ResourceLoader 资源类型校验测试
	 * @Author yangsj
	 * @Date 2019-11-14 21:44
	 **/
	@Test
	public void DefaultResourceLoadertest(){
		ResourceLoader resourceLoader = new DefaultResourceLoader();

		Resource fileResource1 = resourceLoader.getResource("D:/Users/chenming673/Documents/spark.txt");
		System.out.println("fileResource1 is FileSystemResource:" + (fileResource1 instanceof FileSystemResource));

		Resource fileResource2 = resourceLoader.getResource("/Users/chenming673/Documents/spark.txt");
		System.out.println("fileResource2 is ClassPathResource:" + (fileResource2 instanceof ClassPathResource));

		Resource urlResource1 = resourceLoader.getResource("file:/Users/chenming673/Documents/spark.txt");
		System.out.println("urlResource1 is UrlResource:" + (urlResource1 instanceof UrlResource));

		Resource urlResource2 = resourceLoader.getResource("http://www.baidu.com");
		System.out.println("urlResource1 is urlResource:" + (urlResource2 instanceof  UrlResource));

		FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();

		Resource fileResource3 = fileSystemResourceLoader.getResource("D:/Users/chenming673/Documents/spark.txt");

		System.out.println("fileResource3 is FileSystemResource : " + (fileResource3 instanceof FileSystemResource));
	}

	/**
	 * @Description ResourcePatternResolver 加载多个资源
	 * @Author yangsj
	 * @Date 2019-11-14 21:45
	 **/
	@Test
	public void ResourcePatternResolverTest() throws IOException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		//classpath: 表示加载当前类路径中所有匹配的资源
		Resource[] resources = resourcePatternResolver.getResources("classpath:/bulid/classes/java/test/org/springframework/beans/");
		for (Resource resource: resources) {
			System.out.println(resource.getFilename());
		}
		System.out.println("---------------------------");
		//classpath*: 表示加载类路径中所有匹配的资源
		resources = resourcePatternResolver.getResources("classpath*:/");
		for(Resource resource : resources){
			System.out.println(resource.getURL().getPath()); //文件绝对路径
		}


	}
	@Test
	public void dtdValidationAutodetect() {
		doTestValidation("validateWithDtd.xml");
	}

	@Test
	public void xsdValidationAutodetect() {
		doTestValidation("validateWithXsd.xml");
	}

	private void doTestValidation(String resourceName) {
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		Resource resource = new ClassPathResource(resourceName, getClass());
		new XmlBeanDefinitionReader(factory).loadBeanDefinitions(resource);
		TestBean bean = (TestBean) factory.getBean("testBean");
		assertThat(bean).isNotNull();
	}

}
