
package com.newlearn.javamelody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.IGNORED_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);



	@Override
	protected void configure(HttpSecurity http) throws Exception {

	    //http.httpBasic().and().authorizeRequests().antMatchers("/monitoring").hasRole("ADMIN").anyRequest().permitAll();
		http.csrf().disable();

	}


	/*
	 * @Bean
	 * 
	 * @Override public UserDetailsService userDetailsService() { UserDetails user =
	 * createUser("USER","USER16d26298","16d26298-3e81-435b-8e58-d49d4d798964");
	 * UserDetails user1 =
	 * createUser("USER","USER3c0b47cc","3c0b47cc-13b1-448d-92a9-4a8bb65edc1a");
	 * UserDetails user2 =
	 * createUser("ADMIN","USER41974568","41974568-5689-4882-8b4b-601fe3707762");
	 * UserDetails admin =
	 * createUser("ADMIN","USER77fae508","77fae508-1d23-4864-8aae-dfa4bd060fec");
	 * UserDetails admin1 =
	 * createUser("ADMIN","USERb6683814","b6683814-74c0-46e9-8dc2-6f976cb3c2ce");
	 * 
	 * return new InMemoryUserDetailsManager(user,user1,user2, admin,admin1); }
	 * 
	 * 
	 * private UserDetails createUser(String usertype,String username,String
	 * passkey) { UserDetails user =
	 * User.withDefaultPasswordEncoder().username(username).password(passkey)
	 * .roles(usertype).build(); return user; }
	 */



	/* (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
		web
		.ignoring()
		.antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**","/favicon.***");
	}
	
	 
}

