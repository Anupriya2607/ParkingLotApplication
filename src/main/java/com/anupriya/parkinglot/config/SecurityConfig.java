package com.anupriya.parkinglot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final AdminProperties adminProperties;

    public SecurityConfig(AdminProperties adminProperties) {
        this.adminProperties = adminProperties;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, OidcUserService oidcUserService) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health", "/h2-console/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/parking/**").hasAnyRole("USER","ADMIN")
                        .anyRequest().authenticated()
                )
                .headers(h -> h.frameOptions(f -> f.sameOrigin()))
                .oauth2Login(o -> o.userInfoEndpoint(u -> u.oidcUserService(oidcUserService)))
                .logout(l -> l.logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public OidcUserService oidcUserService() {
        OidcUserService delegate = new OidcUserService();
        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) {
                OidcUser oidcUser = delegate.loadUser(userRequest);
                Set<GrantedAuthority> mapped = new HashSet<>(oidcUser.getAuthorities());
                String email = null;
                if (oidcUser.getUserInfo() != null) {
                    email = oidcUser.getUserInfo().getEmail();
                }
                if (email == null) {
                    Object claimEmail = oidcUser.getClaims().get("email");
                    if (claimEmail instanceof String s) email = s;
                }
                if (email != null && adminProperties.getEmails().contains(email.toLowerCase())) {
                    mapped.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                } else {
                    mapped.add(new SimpleGrantedAuthority("ROLE_USER"));
                }
                return new DefaultOidcUser(mapped, oidcUser.getIdToken(), oidcUser.getUserInfo());
            }
        };
    }
}
