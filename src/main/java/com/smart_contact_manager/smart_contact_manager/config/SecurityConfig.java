package com.smart_contact_manager.smart_contact_manager.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.smart_contact_manager.smart_contact_manager.service.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
//    public UserDetailsService userDetailsService() {
//    	
//    	UserDetails user1 =  User.withDefaultPasswordEncoder()
//    			.username("admin123")
//    			.password("admin123").roles("ADMIN","USER").build();
//    	var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1);
//    	return inMemoryUserDetailsManager;
//    }
	@Autowired
	private SecurityCustomUserDetailService securityUserDetailService;
	@Autowired
	private OAuthenticationSuccessHandler oAuthHandler;
	@Autowired
	private AuthFailureHandler authFailureHandler;
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(securityUserDetailService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpsecurity) throws Exception {
		
		httpsecurity.authorizeHttpRequests(authorize->{
			//authorize.requestMatchers("/home","/signup","/services").permitAll();
		authorize.requestMatchers("/user/**").authenticated();
		authorize.anyRequest().permitAll();
		});
		
		httpsecurity.formLogin(formLogin->{
			formLogin.loginPage("/login")
	         .defaultSuccessUrl("/user/profile", true)
	         .failureUrl("/login?error=true")
	         .usernameParameter("email")
	         .passwordParameter("password");


			
//			formLogin.failureHandler(new AuthenticationFailureHandler() {
//				
//				@Override
//				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//						AuthenticationException exception) throws IOException, ServletException {
//					// TODO Auto-generated method stub
//					
//				}
//			});
//			formLogin.successHandler(new AuthenticationSuccessHandler() {
//				
//				@Override
//				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//						Authentication authentication) throws IOException, ServletException {
//					// TODO Auto-generated method stub
//					
//				}
//			});
			
			formLogin.failureHandler(authFailureHandler);
		});
		httpsecurity.csrf(AbstractHttpConfigurer::disable);
		httpsecurity.logout(logoutForm->{
			logoutForm.logoutUrl("/logout");
			logoutForm.logoutSuccessUrl("/login?logout=true");
		});
		
		httpsecurity.oauth2Login(oAuthLogin->{
			oAuthLogin.loginPage("/login");
			oAuthLogin.successHandler(oAuthHandler);
		});
		return httpsecurity.build();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
