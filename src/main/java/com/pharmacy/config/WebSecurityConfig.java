package com.pharmacy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails patient =
             User.withDefaultPasswordEncoder()
                .username("patient")
                .password("password")
                .roles("USER")
                .build();

        UserDetails doctor =
                User.withDefaultPasswordEncoder()
                        .username("doctor")
                        .password("password")
                        .roles("DOCTOR")
                        .build();


        UserDetails pharmacist =
                User.withDefaultPasswordEncoder()
                        .username("pharmacist")
                        .password("password")
                        .roles("PHARMACIST")
                        .build();

        return new InMemoryUserDetailsManager(patient,doctor,pharmacist);
    }
}