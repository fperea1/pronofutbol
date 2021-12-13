package com.base.rest.security;

import static com.base.rest.security.ConstantesToken.HEADER_AUTHORIZACION_KEY;
import static com.base.rest.security.ConstantesToken.TOKEN_BEARER_PREFIX;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

    @Autowired
    private TokenProvider jwtTokenUtil;
    
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_AUTHORIZACION_KEY);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_BEARER_PREFIX)) {
            authToken = header.replace(TOKEN_BEARER_PREFIX,"");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
        		res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Se ha producido un error al recuperar el nombre del usuario del token.");
            } catch (ExpiredJwtException e) {
        		res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Token caducado.");
            } catch(SignatureException e) {
        		res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Se ha producido un error. Username o Password no son válidos.");
            }
        } else {
            logger.warn("No se ha podido encontrar la cadena del token en la cabecera. Se ignora la cabecera.");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken, userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("Usuario autentificado: " + username + ". Estableciendo contexto de seguridad.");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }


}
