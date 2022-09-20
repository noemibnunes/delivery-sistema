package com.testedelivey.service.implementacao;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.testedelivey.model.entity.Cliente;
import com.testedelivey.model.repository.ClienteRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
	
	private ClienteRepository clienteRepository;

	public SecurityUserDetailsService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// codigo para pegar o user id
		Cliente userEncontrado = clienteRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Email não cadastrado!"));

		return User.builder().username(userEncontrado.getNome()).password(userEncontrado.getSenha()).roles("USER")
				.build();

	}
}
