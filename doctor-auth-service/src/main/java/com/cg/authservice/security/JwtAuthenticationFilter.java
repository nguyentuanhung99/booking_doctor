
package com.cg.authservice.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilter {

  private static final long serialVersionUID = 6184814621681607677L;

  private final JwtProvider jwtProvider;

  /**
   * Explicit qualifier because spring has its own implementations
   */
  @Qualifier("jwtUserDetailsServiceImpl")
  private final UserDetailsService userDetailsService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      String jwt = getJwtFromRequest((HttpServletRequest) request);
      if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
        String username = jwtProvider.getUsernameFromJwt(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        SecurityContextHolder.getContext()
          .setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
      }
    } catch (Exception e) {
      log.info("Error Occured");
    }
    chain.doFilter(request, response);

  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer"))
      return bearerToken.substring(7);
    return bearerToken;
  }

}
