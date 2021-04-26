package com.lambdaschool.java.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  private static final String CLIENT_ID = System.getenv("OAUTHCLIENTID");
  private static final String CLIENT_SECRET = System.getenv("OAUTHCLIENTSECRET");

  private static final String GRANT_TYPE_PASSWORD = "password";
  private static final String AUTHORIZATION_CODE = "authorization_code";
  private static final String SCOPE_READ = "read";
  private static final String SCOPE_WRITE = "write";
  private static final String SCOPE_TRUST = "trust";

  private static final int ACCESS_TOKEN_VALIDITY_SECONDS = -1;

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.tokenStore(tokenStore)
                        .authenticationManager(authenticationManager);
    endpoints.pathMapping("/oauth/token", "/login");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
           .withClient(CLIENT_ID)
           .secret(passwordEncoder.encode(CLIENT_SECRET))
           .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE)
           .scopes(SCOPE_READ, SCOPE_WRITE, SCOPE_TRUST)
           .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
  }
}