package com.myhome.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private javax.sql.DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
		.authorizeHttpRequests()
			.antMatchers("/", "/account/register", "/css/**").permitAll() // 누구나 접근할 수 있는 페이지
			.anyRequest().authenticated() // 그 외 페이지는 인증 되어야 함.
			.and()
		.formLogin()
			.loginPage("/account/login") // 인증을 위한 페이지 설정.
			.permitAll()
			.and()
		.logout()
			.permitAll();
	}
	
//  **연관관계 매핑관련**	
//	@OneToOne >> user - user_detail
//	@OneToMany >> user - board
//	@ManyToOne >> board - user
//	@ManyToMany >> user - role
	
	
	//jdbc 이용한 인증설정
	// 사용자 테이블, 권한 테이블 관계 설정
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
	  throws Exception {
	    auth.jdbcAuthentication()
	    	.dataSource(dataSource)
	    	.passwordEncoder(passwordEncoder()) // 비밀번호 암호화 설정
	      .usersByUsernameQuery("select username, password, enabled "
	        + "from user "
	        + "where username = ?") // ?엔 알아서 username이 들어감, Authentication(인증처리) 로그인 설정
	      .authoritiesByUsernameQuery("select u.username, r.name "
	        + "from user_role ur inner join user u on ur.user_id = u.id "
	    	+ "inner join role r on ur.role_id = r.id "	  
	        + "where u.username = ?"); //Authorization(권한처리)권한 설정
	}
	
	
	
	@Bean 
	public PasswordEncoder passwordEncoder() { // 비밀번호를 암호화 해줌
	    return new BCryptPasswordEncoder();
	}
	
}
