package com.jbl.ibank.rest.api.config;

import com.jbl.ibank.rest.api.service.CustomUserDetailsService;
import com.jbl.ibank.rest.api.utils.CustomLoginFailureHandler;
import com.jbl.ibank.rest.api.utils.CustomLoginSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	// @Autowired
    // UserDetailsService userDetailsService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(customUserDetailsService).passwordEncoder(getPasswordEncoder());
	}

	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/assets/**","/login*").permitAll()
                .antMatchers("/**").hasAnyAuthority("ADMIN", "CREATOR", "VIEWER")
				// .antMatchers("/api/token").permitAll()
                .anyRequest().authenticated()
                .and()
				// exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				// .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .successHandler(loginSuccessHandler)
                    .failureHandler(loginFailureHandler)
                    .permitAll()
                .and()
				
                .logout().logoutSuccessUrl("/login?logout").permitAll();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomLoginFailureHandler loginFailureHandler;
     
    @Autowired
    private CustomLoginSuccessHandler loginSuccessHandler;

	
}
