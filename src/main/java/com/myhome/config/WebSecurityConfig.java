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
			.antMatchers("/", "/css/**").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/account/login")
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
	
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
	  throws Exception {
	    auth.jdbcAuthentication()
	    	.dataSource(dataSource)
	    	.passwordEncoder(passwordEncoder()) // 비밀번호 암호화 설정
	      .usersByUsernameQuery("select username, password, enabled "
	        + "from user "
	        + "where username = ?") // ?엔 알아서 username이 들어감, Authentication 로그인 설정
	      .authoritiesByUsernameQuery("select username, name "
	        + "from user_role ur inner join user u on ur.user_id = u.id "
	    	+ "inner join role r on ur.role_id = r.id "	  
	        + "where email = ?"); //Authentication 권한 설정
	}
	
	
	
	@Bean 
	public PasswordEncoder passwordEncoder() { // 비밀번호를 암호화 해줌
	    return new BCryptPasswordEncoder();
	}
	
}
