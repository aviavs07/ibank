package com.jbl.ibank.rest.api.config;

import com.jbl.ibank.rest.api.service.CustomUserDetailsService;
import com.jbl.ibank.rest.api.service.JwtUserDetailsService;
import com.jbl.ibank.rest.api.utils.CustomLoginFailureHandler;
import com.jbl.ibank.rest.api.utils.CustomLoginSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MultiHttpSecurityConfig {

    @Order(2)
    @Configuration
    public class AdminWebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        @Bean
        public PasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationProvider authProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(customUserDetailsService);
            provider.setPasswordEncoder(getPasswordEncoder());

            return provider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            // auth.authenticationProvider(authenticationProvider);
            auth.authenticationProvider(authProvider());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.exceptionHandling().accessDeniedPage("/403");

            http.requestMatchers().and().csrf().disable().authorizeRequests().antMatchers("/assets/**", "/login*").permitAll()
                    .antMatchers("/**").hasAnyAuthority("ADMIN", "CREATOR", "VIEWER").anyRequest().authenticated().and()
                    .formLogin().loginPage("/login").usernameParameter("username").successHandler(loginSuccessHandler)
                    .failureHandler(loginFailureHandler).permitAll().and().logout().logoutSuccessUrl("/login?logout")
                    .permitAll();
        }

        @Autowired
        private CustomLoginFailureHandler loginFailureHandler;

        @Autowired
        private CustomLoginSuccessHandler loginSuccessHandler;
        // @Override
        // public void configure(WebSecurity web) throws Exception {
        // Web.ignoring().antMatchers("/js/**","/bootstarp/**","/css/**","/fonts/**","/images/**","/
        // Index/**","/medicineFile/**","/validate/**");//Release static resources
        // }
    }

    @Order(1)
    @Configuration
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        @Autowired
        private JwtUserDetailsService customUserDetailsService;

        @Autowired
        private JwtRequestFilter jwtRequestFilter;

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            // configure AuthenticationManager so that it knows from where to load
            // user for matching credentials
            // Use BCryptPasswordEncoder
            auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            // We don't need CSRF for this example
            httpSecurity.csrf().disable()
                    // dont authenticate this particular request
                    // .authorizeRequests().antMatchers("/employee").authenticated().//.authorizeRequests().antMatchers("/cbs/**").authenticated().

                    // only allow authenticate request without access token
                    .authorizeRequests().antMatchers("/api/token").permitAll().
                    // all other requests need to be authenticated
                    anyRequest().authenticated().and().
                    // make sure we use stateless session; session won't be used to
                    // store user's state.
                    exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            httpSecurity.headers().frameOptions().disable();
            // Add a filter to validate the tokens with every request
            httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

    
}
