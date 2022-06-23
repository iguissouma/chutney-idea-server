package com.chutneytesting;

import com.chutneytesting.security.api.UserDto;
import com.chutneytesting.security.domain.Authorization;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Order(-1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    public SecurityConfiguration() {
    }

    protected void configure(HttpSecurity http
    ) throws Exception {
        UserDto defaultUser = getDefaultUser();
        http.csrf().disable()
                .anonymous()
                .principal(defaultUser)
                .authorities(new ArrayList<>(defaultUser.getAuthorities()))
                .and()
                .authorizeRequests()
                .antMatchers(new String[]{"/**", "/api/**"}).permitAll();
        http.requiresChannel().anyRequest().requiresInsecure();
    }

    protected UserDto getDefaultUser() {
        UserDto defaultUser = new UserDto();
        defaultUser.setName("ChutneyPluginUser");
        defaultUser.addRole("ADMIN");
        Arrays.stream(Authorization.values()).map(Enum::name).forEach(defaultUser::grantAuthority);
        return defaultUser;
    }
}

