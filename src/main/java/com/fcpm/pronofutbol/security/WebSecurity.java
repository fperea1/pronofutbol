package com.fcpm.pronofutbol.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.common.collect.ImmutableList;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	
	Logger logger = LoggerFactory.getLogger(WebSecurity.class);

	private UserDetailsService userDetailsService;
	
	public WebSecurity(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
		return authConfiguration.getAuthenticationManager();
    }
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilterBean() {
		return new JwtAuthenticationFilter();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		/*
		 * 1. Se desactiva el uso de cookies
		 * 2. Se activa la configuración CORS con los valores por defecto
		 * 3. Se desactiva el filtro CSRF
		 * 4. Se indica que el login no requiere autenticación
		 * 5. Se indica que el resto de URLs esten securizadas
		 * 
		 */		 
    
		httpSecurity
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.cors().and()
		.csrf().disable()
		.authorizeHttpRequests((authz) -> authz
				.requestMatchers("/usuarios/**", "/logs/**", "/configuracion/**", "/quinielas/**", "/ligas/**", "/paises/**", "/tiposSorteo/**").hasAnyAuthority("SUPERUSUARIO")
				.requestMatchers("/logout", "/cambioPassword").authenticated() //Luego comentaré esa línea, la dejo por tenerlo controlado
				.requestMatchers("/autenticacion/generate-token", "/contacto/envioConsulta").permitAll()
                .anyRequest().authenticated()
            )
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
		
		httpSecurity.authenticationProvider(authenticationProvider());
		
		// XSS y CSP
		httpSecurity.headers()
	        .xssProtection()
	        .and()
	        .contentSecurityPolicy("style-src 'self'; script-src 'self'; form-action 'self'");
		
		httpSecurity
				.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(bCryptPasswordEncoder());
	    return authProvider;
	}
	
//	@Bean
//	  public WebSecurityCustomizer webSecurityCustomizer() {
//	    return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico"); 
//	  }
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		config.setAllowedOrigins(ImmutableList.of("http://localhost:4200", "https://localhost:4200", "http://www.pronofutbol.com"));
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
