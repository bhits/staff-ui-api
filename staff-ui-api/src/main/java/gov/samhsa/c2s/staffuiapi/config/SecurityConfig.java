package gov.samhsa.c2s.staffuiapi.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import static gov.samhsa.c2s.common.oauth2.OAuth2ScopeUtils.hasScopes;

@Configuration
public class SecurityConfig {

    private static final String RESOURCE_ID = "staffUiApi";

    @Bean
    public ResourceServerConfigurer resourceServer(SecurityProperties securityProperties) {
        return new ResourceServerConfigurerAdapter() {
            @Override
            public void configure(ResourceServerSecurityConfigurer resources) {
                resources.resourceId(RESOURCE_ID);
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                if (securityProperties.isRequireSsl()) {
                    http.requiresChannel().anyRequest().requiresSecure();
                }
                http.authorizeRequests()
                        .antMatchers(HttpMethod.GET, "/ums/**").access(hasScopes("staffUiApi.read"))
                        .antMatchers(HttpMethod.POST, "/ums/**").access(hasScopes("staffUiApi.write"))
                        .antMatchers(HttpMethod.DELETE, "/ums/**").access(hasScopes("staffUiApi.write"))
                        .antMatchers(HttpMethod.PUT, "/ums/**").access(hasScopes("staffUiApi.write"))
                        .antMatchers(HttpMethod.OPTIONS, "/ums/**").access(hasScopes("staffUiApi.write"))
                        .anyRequest().denyAll();
            }
        };
    }
}