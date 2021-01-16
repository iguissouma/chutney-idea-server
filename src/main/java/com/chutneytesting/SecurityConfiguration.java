package com.chutneytesting;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(-1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    public SecurityConfiguration() {
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(new String[]{"/**", "/api/**"}).permitAll();
        http.requiresChannel().anyRequest().requiresInsecure();
    }
}
