package org.cd.spring.bibliotheque.model;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.cd.spring.bibliotheque.service.JwtService;
import org.cd.spring.bibliotheque.service.ServiceUtilisateur;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FiltreAuthentificationJwt extends OncePerRequestFilter {
    private final JwtService serviceJwt;
    private final ServiceUtilisateur serviceUtilisateur;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String enteteAuth = request.getHeader("Authorization");
        final String jwt;
        final String emailUtilisateur;

        if (StringUtils.isEmpty(enteteAuth) || !StringUtils.startsWith(enteteAuth, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = enteteAuth.substring(7);
        emailUtilisateur = serviceJwt.extraireNomUtilisateur(jwt);

        if (StringUtils.isNotEmpty(emailUtilisateur)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = serviceUtilisateur.serviceDetailsUtilisateur()
                    .loadUserByUsername(emailUtilisateur);

            if (serviceJwt.estTokenValide(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }

        filterChain.doFilter(request, response);
    }
}
