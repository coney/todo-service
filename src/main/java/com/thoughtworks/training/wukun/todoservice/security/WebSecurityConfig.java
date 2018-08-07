package com.thoughtworks.training.wukun.todoservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.AbstractEndpointMvcAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private List<AbstractEndpointMvcAdapter<? extends Endpoint<?>>> actuatorEndpoints;

    @Autowired
    private InternalAuthenticationTokenFilter internalAuthenticationTokenFilter;

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(actuatorEndpoints.stream().map(AbstractEndpointMvcAdapter::getPath).toArray(String[]::new)).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(internalAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint);
    }
}
