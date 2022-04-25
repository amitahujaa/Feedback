package com.onlyflights.source.configurations;

import com.onlyflights.source.services.CustomerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	AuthHandler authHandler;

	@Autowired
	private BCryptPasswordEncoder passwordBcrypt;

	@Bean
	public UserDetailsService jpaUserDetails() {
		return new CustomerDetailService();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsService detailsService = jpaUserDetails();
		auth.userDetailsService(detailsService).passwordEncoder(passwordBcrypt);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.headers().frameOptions().sameOrigin().contentSecurityPolicy("frame-ancestors 'self'");

		http.authorizeRequests().antMatchers("/").hasAuthority("ADMIN").antMatchers("/h2/console").permitAll()
				.antMatchers("/signin").permitAll().antMatchers("/register").permitAll().antMatchers("/feedback")
				.hasAuthority("ADMIN").antMatchers("/home/**").hasAuthority("ADMIN").anyRequest().authenticated().and()
				.csrf().disable().formLogin().successHandler(authHandler).loginPage("/signin")
				.failureUrl("/signin?error=true").usernameParameter("email").passwordParameter("password").and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and()
				.exceptionHandling();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/static/**", "/resources/**", "/js/**", "/static/images/**", "/css/**");
	}

}
