package com.testedelivey.service;

import java.util.List;
import java.util.Optional;

import com.testedelivey.model.entity.Funcionario;

public interface IFuncionarioService {
	
	Funcionario cadastrarFuncionario(Funcionario usuario);

	Funcionario autenticarFuncionario(String nome, String senha);

	Optional<Funcionario> obterPorId(Long id);

	List<Funcionario> obterPorTodos();

	Funcionario atualizarFuncionario(Funcionario funcionario);

	void deletarFuncionario(Funcionario funcionario);

	void validarFuncionario(Funcionario Funcionario);

}
