/*
 * This file is part of the WannaGo distribution (https://github.com/wannago).
 * Copyright (c) [2019] - [2020].
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */


package org.wannagoframework.backend.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  private final ResourceServerProperties sso;

  private final OAuth2ClientContext oAuth2ClientContext;

  @Autowired
  public ResourceServerConfig(ResourceServerProperties sso, OAuth2ClientContext oAuth2ClientContext) {
    this.sso = sso;
    this.oAuth2ClientContext = oAuth2ClientContext;
  }

  @Bean
  @ConfigurationProperties(prefix = "security.oauth2.client")
  public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
    return new ClientCredentialsResourceDetails();
  }

  @Bean
  public RequestInterceptor oauth2FeignRequestInterceptor() {
    return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(),
        clientCredentialsResourceDetails());
  }

  @Bean
  public RequestInterceptor oauth2FeignRequestInterceptor2() {
    return template -> {
      if (template.body() == null) {
        return;
      }
      ObjectMapper mapper = new ObjectMapper();
      JsonNode node = null;
      try {
        node = mapper.readTree(template.body());
        String username = node.path("_username").asText();
        if (StringUtils.isNotBlank(username) && !"null".equals(username)) {
          template.header("X-SecUsername", username);
        }
        String securityUserId = node.path("_securityUserId").asText();
        if (StringUtils.isNotBlank(securityUserId) && !"null".equals(securityUserId)) {
          template.header("X-SecSecurityUserId", securityUserId);
        }
        String sessionId = node.path("_sessionId").asText();
        if (StringUtils.isNotBlank(sessionId) && !"null".equals(sessionId)) {
          template.header("X-SecSessionId", sessionId);
        }
        String iso3Language = node.path("_iso3Language").asText();
        if (StringUtils.isNotBlank(iso3Language) && !"null".equals(iso3Language)) {
          template.header("X-Iso3Language", iso3Language);
        }
      } catch (Throwable t) {
      }
    };
  }

  @Bean
  public OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext) {
    return new OAuth2RestTemplate(clientCredentialsResourceDetails(), oauth2ClientContext);
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    Resource resource = new ClassPathResource("public.txt");
    String publicKey = null;
    try {
      publicKey = IOUtils.toString(resource.getInputStream(), Charset.defaultCharset().name());
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
    converter.setVerifierKey(publicKey);
    return converter;
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    return defaultTokenServices;
  }

  /*
  @Bean
  @Primary
  public ResourceServerTokenServices resourceServerTokenServices() {
    return new UserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
  }
*/
  @Override
  public void configure(ResourceServerSecurityConfigurer config) {
    config.tokenServices(tokenServices());
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.headers().frameOptions().sameOrigin();
    http.csrf().disable();
    http.authorizeRequests()
        .antMatchers("/ping").permitAll()
        .antMatchers("/h2-console/**").permitAll()
        .antMatchers("/actuator/health").permitAll()
        .antMatchers("/actuator/info").permitAll()
        .antMatchers("/actuator/prometheus").permitAll()
        .antMatchers("/actuator/hystrix.stream").permitAll()
        .antMatchers("/actuator/**").hasAuthority("MONITORING")
        .antMatchers("/monitoring/**").hasAuthority("MONITORING")
        .anyRequest().authenticated();
  }
}