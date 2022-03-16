package com.base.rest.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.rest.entities.Rol;
import com.base.rest.entities.Usuario;
import com.base.rest.repositories.UsuarioRepository;

@Service(value = "userDetailsService")
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.getByUsername(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Error. No existe usuario con Username " + username);
		}
		return new User(usuario.getUsername(), usuario.getPassword(), getAuthority(usuario.getRoles()));
	}

	private Set<SimpleGrantedAuthority> getAuthority(Set<Rol> roles) {
		
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		roles.forEach(rol -> authorities.add(new SimpleGrantedAuthority(rol.getNombre())));
		return authorities;
	}

}
