package io.paycorp.smartmandate.demo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Value("#{${credential.map}}")
        private Map<String, String> credentialMap;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .permitAll());
                return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
                List<UserDetails> users = credentialMap.entrySet().stream()
                                .map(entry -> User.withDefaultPasswordEncoder()
                                                .username(entry.getKey())
                                                .password(entry.getValue())
                                                .roles("USER")
                                                .build())
                                .collect(Collectors.toList());

                return new InMemoryUserDetailsManager(users);
        }

}
