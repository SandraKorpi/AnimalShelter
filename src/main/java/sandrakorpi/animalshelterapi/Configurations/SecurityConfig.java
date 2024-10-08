package sandrakorpi.animalshelterapi.Configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sandrakorpi.animalshelterapi.Security.JwtAuthenticationFilter;

import java.util.List;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${frontend.url}")
    private String frontendUrl;

    private static final String[] ALLOWED_HEADERS = {
            CONTENT_TYPE,
            AUTHORIZATION,
            ORIGIN,
            ACCEPT,
            ACCESS_CONTROL_ALLOW_ORIGIN,
            ACCESS_CONTROL_REQUEST_METHOD,
            ACCESS_CONTROL_REQUEST_HEADERS,
            ACCESS_CONTROL_ALLOW_CREDENTIALS
    };
    //Tillåta swagger.
    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/webjars/**"
    };

    @Autowired
    public SecurityConfig(
            AuthenticationProvider authenticationProvider,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/auth/**").permitAll();
                    auth.requestMatchers(AUTH_WHITELIST).permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of(frontendUrl));
        configuration.setAllowedHeaders(List.of(ALLOWED_HEADERS));
        configuration.setExposedHeaders(List.of(ALLOWED_HEADERS));
        configuration.setAllowedMethods(List.of(POST.name(), GET.name(), PUT.name(), DELETE.name(), PATCH.name(), OPTIONS.name()));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
