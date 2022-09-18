package com.testedelivey.service;

import java.util.List;
import java.util.Optional;

import com.testedelivey.model.entity.Cliente;

public interface IClienteService {
	
	Cliente cadastrarUsuario(Cliente usuario);

	void validarEmail(String email);

	Cliente autenticarUsuario(String email, String senha);

	Optional<Cliente> obterPorId(Long id);

	List<Cliente> obterPorTodos();

	Cliente atualizarCliente(Cliente cliente);

	void deletarCliente(Cliente cliente);

	void validarUsuario(Cliente cliente);

}
