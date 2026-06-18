package com.alediesme.joyeria.shared.config.security;

import com.alediesme.joyeria.security.adapter.in.web.JsonAccessDeniedHandler;
import com.alediesme.joyeria.security.adapter.in.web.JsonAuthenticationEntryPoint;
import com.alediesme.joyeria.security.adapter.in.web.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      JwtAuthenticationFilter jwtAuthenticationFilter,
      JsonAuthenticationEntryPoint jsonAuthenticationEntryPoint,
      JsonAccessDeniedHandler jsonAccessDeniedHandler)
      throws Exception {
    http.csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(jsonAuthenticationEntryPoint)
        .accessDeniedHandler(jsonAccessDeniedHandler)
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/auth/login")
        .permitAll()
        .antMatchers("/actuator/health", "/actuator/health/**")
        .permitAll()
        .antMatchers("/actuator/**")
        .hasAuthority("ROLE_ADMIN")
        .antMatchers(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
