package io.swit.ctsimapserver.jwt;

import io.swit.ctsimapserver.component.JwtUtil;
import io.swit.ctsimapserver.service.com.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      try {
          String jwt = parseJwt(request);
          if (jwt != null && jwtUtil.vaildateJwtToken(jwt)) {
              String email = jwtUtil.getUserNameFromJwtToken(jwt);

              var userDetails = userService.loadUserByUsername(email);
              UsernamePasswordAuthenticationToken authentication =
                      new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
              SecurityContextHolder.getContext().setAuthentication(authentication);
          }
      } catch (Exception e) {
          log.error("Cannot set user authentication: ", e);
      }

      filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
