/**
 * 
 */
package com.side.service.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.side.service.auth.service.passwordEncoder.MyPasswordEncoder;
import com.side.service.auth.service.userDetailsService.MyDaoAuthenticationProvider;
import com.side.service.auth.service.userDetailsService.UserDetailsServiceImpl;


/**
 * @author gmc
 *
 */
@Configuration
@EnableWebSecurity
public class AuthServerWebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsServiceImpl userDetailsService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.anyRequest()
		.authenticated();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		MyDaoAuthenticationProvider provider = new MyDaoAuthenticationProvider(userDetailsService, new MyPasswordEncoder() {
			
		});
		auth.authenticationProvider(provider);
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
       web.ignoring().antMatchers("/js/**", "/images/**", "/css/**", "/common/**", "/pages/**");
    }
	
	@Bean
	public MyPasswordEncoder passwordEncoder() {
	    return new MyPasswordEncoder();
	}
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
}
