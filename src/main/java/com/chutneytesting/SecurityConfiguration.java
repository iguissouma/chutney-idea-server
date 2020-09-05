package com.chutneytesting;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    public SecurityConfiguration() {
    }

    protected void configure(HttpSecurity http) throws Exception {
        ((AuthorizedUrl) ((HttpSecurity) http.csrf().disable()).authorizeRequests().antMatchers(new String[]{"/"})).permitAll();
    }
}
