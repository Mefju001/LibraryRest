package com.library.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class Security {

   /* @Bean
    public UserDetailsService users() {
        // The builder will ensure the passwords are encoded before saving in memory
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users
                .username("user")
                .password("{noop}password")
                .roles("USER")
                .build();
        UserDetails admin = users
                .username("admin")
                .password("{noop}password")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }*/



     @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource)
    {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Zapytanie SQL do pobierania użytkowników na podstawie nazwy użytkownika
        userDetailsManager.setUsersByUsernameQuery(
                "SELECT Username, Password, enabled FROM users WHERE Username = ?");

        // Zapytanie SQL do pobierania ról użytkownika na podstawie nazwy użytkownika
        userDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT u.Username, a.authority " +
                        "FROM users u " +
                        "JOIN authorities a ON u.Role_id = a.authority_id " +
                        "WHERE u.Username = ?");

        return userDetailsManager;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(configurer->
                        configurer
                                .requestMatchers(HttpMethod.GET,"/Books").hasAnyRole("ROOT","USER")

                );
        http.httpBasic();
        //http.csrf().disable();
        return http.build();
    }

}
