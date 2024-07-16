package com.library.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.io.IOException;

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
                                .requestMatchers(HttpMethod.GET,"/Books","/Books/Library","/Books/Library/{id}",
                                "/Books/Library/Book/{id}","/Books/{id}","/Books/Sortby/{Sort}/{Type}",
                                "/Books/Author","/Books/SearchBy","/Books/Price","/Library/All","/Books/Year").hasAnyRole("ADMIN","MANAGER","USER")
                                //Manager&Admin
                                .requestMatchers(HttpMethod.POST,"/Books/AddBook","/Library/AddLibrary","/Library/AddBookLibrary").hasAnyRole("ADMIN","MANAGER")
                                .requestMatchers(HttpMethod.PUT,"/Books/UpdateBook","/Library/UpdateLibrary").hasAnyRole("MANAGER","ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/Books/Delete/{id}","/Library/{id}").hasAnyRole("MANAGER","ADMIN")
                                //Admin
                                //.requestMatchers(HttpMethod.GET,"/Books").hasAnyRole("ADMIN","USER")
                                .anyRequest().authenticated()
        );
        http.httpBasic(httpBasicCustomizer -> httpBasicCustomizer
                    .realmName("Logowanie") // Konfiguracja, np. nazwa realm
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // Możesz dodać niestandardowy punkt wejścia

                // Możesz dodać inne konfiguracje
    );
        http.csrf(AbstractHttpConfigurer::disable);
        http.exceptionHandling(customizer -> customizer.authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        return http.build();
    }

}
