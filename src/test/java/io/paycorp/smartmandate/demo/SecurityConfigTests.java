package io.paycorp.smartmandate.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfigTests {

    @Mock
    private HttpSecurity httpSecurity;

    @InjectMocks
    private SecurityConfig securityConfig;

    @Value("#{${credential.map}}")
    private Map<String, String> credentialMap;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        credentialMap = new HashMap<>();
        credentialMap.put("user1@paycorp.io", "password1");
        credentialMap.put("user2@paycorp.io", "password2");
    }

    @Test
    public void testSecurityFilterChain() throws Exception {
        SecurityFilterChain securityFilterChain = securityConfig.securityFilterChain(httpSecurity);
        assertNotNull(securityFilterChain);
    }

    @Test
    public void testUserDetailsService() {
        UserDetailsService userDetailsService = securityConfig.userDetailsService();
        assertNotNull(userDetailsService);
        assert(userDetailsService instanceof InMemoryUserDetailsManager);
    }
}
