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

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  public static final String AUTH_SERVER =
      "http://localhost:8081/spring-security-oauth-server/oauth";
  public static final String CLIENT_ID = "fooClientIdPassword";
  public static final String CLIENT_SECRET = "secret";

  @Bean
  public Docket api() {
    // @formatter:off
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.wannaenjoy.backend.endpoint"))
        .paths(PathSelectors.any())
        .build();
    // .securitySchemes( Arrays.asList( securityScheme() ) )
    // .securityContexts( Arrays.asList( securityContext() ) );
    // @formatter:on
  }

  private ApiInfo apiInfo() {
    // @formatter:off
    return new ApiInfo(
        "Do You Wanna Place REST API",
        "Rest API documentation for Do You Wanna Place project.",
        "API v1.0",
        "Terms of service",
        new Contact("Wannago", "", "Wannago.dev1@gmail.com"),
        "License of API",
        "API license URL",
        Collections.emptyList());
    // @formatter:on
  }

  @Bean
  public SecurityConfiguration security() {
    return SecurityConfigurationBuilder.builder()
        .clientId(CLIENT_ID)
        .clientSecret(CLIENT_SECRET)
        .useBasicAuthenticationWithAccessCodeGrant(true)
        .build();
  }

  private SecurityScheme securityScheme() {
    // @formatter:off
    GrantType grantType =
        new AuthorizationCodeGrantBuilder()
            .tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/token", "oauthtoken"))
            .tokenRequestEndpoint(
                new TokenRequestEndpoint(AUTH_SERVER + "/authorize", CLIENT_ID, CLIENT_ID))
            .build();
    // @formatter:on
    return new OAuthBuilder()
        .name("spring_oauth")
        .grantTypes(Collections.singletonList(grantType))
        .scopes(Arrays.asList(scopes()))
        .build();
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(
            Collections.singletonList(new SecurityReference("spring_oauth", scopes())))
        .forPaths(PathSelectors.regex("/foos.*"))
        .build();
  }

  private AuthorizationScope[] scopes() {
    return new AuthorizationScope[]{
        new AuthorizationScope("read", "for read operations"),
        new AuthorizationScope("write", "for write operations"),
        new AuthorizationScope("foo", "Access foo API")
    };
  }
}
