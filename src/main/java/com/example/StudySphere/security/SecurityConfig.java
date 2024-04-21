package com.example.StudySphere.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {


    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select uid,password,active from users where uid=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select uid,role from users where uid=?");
        return jdbcUserDetailsManager;
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        UserDetails joel = User.builder()
//                .username("joel")
//                .password("{noop}123")
//                .roles("STUDENT")
//                .build();
//        UserDetails mary = User.builder()
//                .username("mary")
//                .password("{noop}123")
//                .roles("TEACHER")
//                .build();
//        UserDetails susan = User.builder()
//                .username("susan")
//                .password("{noop}123")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(joel,mary,susan);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/student/**").hasAnyRole("STUDENT","ADMIN")
                        .requestMatchers("/teacher/**").hasAnyRole("TEACHER","ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/loginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                )
                .logout(logout-> logout.permitAll());
        return httpSecurity.build();



    }

}
