package com.myhome.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	// 업로드경로 지정
	@Value("${uploadPath}")
	String uploadPath;
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 입력하는 url에 /images로 시작하는 경우 uploadPath에 설정한 폴더를 기준으로 파일을 읽어오도록 설정
		// 저장된 파일을 읽어올 root 경로 설정
		registry.addResourceHandler("/images/**").addResourceLocations(uploadPath);
	}
}
