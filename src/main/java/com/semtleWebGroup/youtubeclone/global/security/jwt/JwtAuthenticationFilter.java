package com.semtleWebGroup.youtubeclone.global.security.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseBearerToken(request);
            expectValid(token);
        } catch (TokenExpiredException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token has expired");
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
        }
        catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }finally {
            filterChain.doFilter(request, response);
        }
    }
    
    private void expectValid(String token) throws Exception {
        if (token == null || token.equalsIgnoreCase("null")||!jwtTokenProvider.validateToken(token)) {
            //TODO: 예외 구체화
            throw new Exception("Token is null or invalid.");
        }
        String userEmail = jwtTokenProvider.getUserEmail(token);
        log.info("Authenticated user Email : " + userEmail);
        //TODO: Member기준 authentication이 아닌 Channel기준으로
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

        
        private String parseBearerToken (HttpServletRequest request){
            String bearerToken = request.getHeader("Authorization");
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
                return bearerToken.substring(7);
            }
            return null;
        }
    }
