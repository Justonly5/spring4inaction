package com.spring.chapter_05.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpittrWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootConfig.class };
	}

	/** 指定配置类 **/
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebConfig.class };
	}

	/** 将DispatcherServlet映射到 "/" **/
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
