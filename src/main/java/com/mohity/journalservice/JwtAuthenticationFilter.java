package com.mohity.journalservice;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtValidationService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);
        Long requestUserId = Long.valueOf(extractPathParams(request.getServletPath()).get("userId"));

        if(jwtService.isValid(token, requestUserId)) {
            String username = jwtService.extractUsername(token);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    username, null, null);

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

    private Map<String, String> extractPathParams(String path) {
        Map<String, String> pathParams = new HashMap<>();

        int lastSlashIndex = path.lastIndexOf('/'); // Find the last slash
        if (lastSlashIndex != -1 && lastSlashIndex < path.length() - 1) { // Check if a slash exists and there's something after it
            String userIdString = path.substring(lastSlashIndex + 1); // Get the part after the last slash
            pathParams.put("userId", userIdString);
        }

        return pathParams;
    }
}
