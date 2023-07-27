package ru.job4j.urlshortcut.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.job4j.urlshortcut.config.security.jwt.JwtAuthorizationFilter;

/**
 * Класс конфигурации Spring Security
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Константа - URL для регистрации в приложении
     */
    private static final String SIGN_UP_URL = "/register";

    /**
     * Константа - URL для аутентификации в приложении
     */
    private static final String SIGN_IN_URL = "/login";

    /**
     * Константа - URL для переадресации по коду в приложении
     */
    private static final String REDIRECT_URL = "/redirect/**";

    /**
     * Зависимость от AuthenticationConfiguration
     */
    private final AuthenticationConfiguration authenticationConfiguration;

    /**
     * Зависимость от UserDetailsService
     */
    private final UserDetailsService userDetailsService;

    /**
     * Настройка бина, отвечающего за безопасность приложения
     * @param http HttpSecurity
     * @return сконфигурированный объект безопасности
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
            .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL, SIGN_IN_URL).permitAll()
                .antMatchers(HttpMethod.GET, REDIRECT_URL).permitAll()
                .anyRequest().authenticated()
            .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    /**
     * Настройка бина - провайдера аутентификации
     * @return объект DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider =
                                        new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    /**
     * Бин в виде AuthenticationManager
     * @return объект AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Бин в виде BCryptPasswordEncoder
     * @return объект BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Настройка бина UrlBasedCorsConfigurationSource
     * @return объект UrlBasedCorsConfigurationSource
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source =
                                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(
                "/**", new CorsConfiguration().applyPermitDefaultValues()
        );
        return source;
    }
}
