package com.library.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class Security {

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
                        "FROM userandrole u " +
                        "JOIN authorities a ON u.authority = a.authority_id " +
                        "WHERE u.Username = ?");
        return userDetailsManager;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http
                .authorizeHttpRequests(configurer->
                        configurer
                                //User
                                .requestMatchers(HttpMethod.GET,"/Books/**","/Library/**","/User/**").permitAll()
                                //Manager&Admin
                                .requestMatchers(HttpMethod.POST,"/Books/","/Library/","/Library/AddBookLibrary").hasAnyRole("ADMIN","MANAGER")
                                .requestMatchers(HttpMethod.PUT,"/Books/","/Library/").hasAnyRole("MANAGER","ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/Books/{id}","/Library/{id}").hasAnyRole("MANAGER","ADMIN")
                                .requestMatchers("/error").hasRole("ADMIN")
                                //Admin
                                //.requestMatchers(HttpMethod.GET,"/Books").hasAnyRole("ADMIN","USER")
                                .anyRequest().authenticated()
        );
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

}