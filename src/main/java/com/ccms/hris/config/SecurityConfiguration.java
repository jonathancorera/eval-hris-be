
/* Security configuration setting for the whole application based on Spring Security
 * 
 */
package com.ccms.hris.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
	
	@Autowired
	private com.ccms.hris.config.JwtRequestFilter jwtRequestFilter;

	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		
		super.configure(auth);
	}
	


	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		//allow access to the authenticate and createcustomer urls without token authentication
		httpSecurity.csrf().disable()
				.authorizeRequests().antMatchers("/authenticate", "/v2/api-docs","/swagger-resources/**",
						"/swagger-ui.html",
						"/v2/api-docs",
						"/webjars/**",
						"/configuration/ui", "/configuration/security", "/swagger-ui.html", "/swagger-ui/", "/webjars/").permitAll().antMatchers(HttpMethod.OPTIONS, "/**")
				.permitAll()
				.antMatchers("/user/create", "/swagger-ui/**")
				.permitAll()
				.antMatchers(HttpMethod.GET, "/books/**").permitAll()
				.antMatchers("/users/delete/").hasAnyAuthority( "ADMIN")
				.anyRequest()
				.authenticated().and().
						exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		httpSecurity.cors().and();
		
		//use filter created for authenticated requests
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return  PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
	
}


