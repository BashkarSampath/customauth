package com.bashkarsampath.app.configurations;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.bashkarsampath.app.enums.UserRole;
import com.bashkarsampath.app.services.user.CustomOAuth2UserService;
import com.bashkarsampath.app.services.user.CustomOauth2UserCustomRole;
import com.bashkarsampath.app.services.user.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	@Primary
	public CustomOAuth2UserService customOAuth2UserService(UserService userService) {
		return new CustomOAuth2UserService(userService, false, UserRole.ROLE_EMPLOYEE.getValue());
	}

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/login", "/oauth/**").permitAll().mvcMatchers("/employee/**")
				.hasAnyAuthority("AUTHORITY_EMPLOYEE", "AUTHORITY_ADMIN", "AUTHORITY_CEO").mvcMatchers("/supervisor/**")
				.hasAnyAuthority("AUTHORITY_SUPERVISOR", "AUTHORITY_ADMIN", "AUTHORITY_CEO").anyRequest()
				.authenticated().and().oauth2Login().loginPage("/login").userInfoEndpoint()
				.userService(customOAuth2UserService).and().successHandler(new AuthenticationSuccessHandler() {
					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
						CustomOauth2UserCustomRole customOauth2UserCustomRole = (CustomOauth2UserCustomRole) authentication
								.getPrincipal();
						log.info("Authentication name: " + authentication.getName() + "; Email: "
								+ customOauth2UserCustomRole.getEmail() + "; Authority: "
								+ customOauth2UserCustomRole.getAuthorities().toString());
					}
				}).defaultSuccessUrl("/home").and().logout().logoutSuccessUrl("/login").permitAll().and()
				.exceptionHandling().accessDeniedPage("/403");
	}
}