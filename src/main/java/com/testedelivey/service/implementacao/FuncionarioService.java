package com.testedelivey.service.implementacao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testedelivey.exception.AutenticacaoException;
import com.testedelivey.exception.RegraNegocioException;
import com.testedelivey.model.entity.Funcionario;
import com.testedelivey.model.repository.FuncionarioRepository;
import com.testedelivey.service.IFuncionarioService;

@Service
public class FuncionarioService implements IFuncionarioService {
	
	private static final String USER_NAO_ENCONTRADO = "Usuário não encontrado!";
	private static final String SENHA_INCORRETA = "Senha Inválida!";
	private static final String NOME_INVALIDO = "Informe um Nome válido!";
	private static final String SENHA_INVALIDA = "Informe uma senha válida!";

	@Autowired
	private FuncionarioRepository repository;
	
	public FuncionarioService(FuncionarioRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Optional<Funcionario> obterPorId(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public List<Funcionario> obterPorTodos() {
		return repository.findAll();
	}
	
	@Transactional
	public Funcionario cadastrarFuncionario(Funcionario Funcionario) {
		validarFuncionario(Funcionario);
		return repository.save(Funcionario);
	}

	@Override
	public Funcionario autenticarFuncionario(String nome, String senha) {
		
		Optional<Funcionario> usuario = repository.findByNome(nome); 
		
		if(!usuario.isPresent()) {
			throw new AutenticacaoException(USER_NAO_ENCONTRADO);
		}
		
		if(!senha.trim().equals(usuario.get().getSenha().trim())){
			throw new AutenticacaoException(SENHA_INCORRETA);
		}
		
		return usuario.get();
	}
	
	@Override
	@Transactional
	public Funcionario atualizarFuncionario(Funcionario Funcionario) {
		Objects.requireNonNull(Funcionario.getId());
		validarFuncionario(Funcionario);
		return repository.save(Funcionario);
	}

	@Override
	@Transactional
	public void deletarFuncionario(Funcionario Funcionario) {
		Objects.requireNonNull(Funcionario.getId());
		repository.delete(Funcionario);
	}
	
	@Override
	public void validarFuncionario(Funcionario Funcionario) {
		if(Funcionario.getNome() == null || Funcionario.getNome().trim().equals("")) {
			throw new RegraNegocioException(NOME_INVALIDO);
		}
		
		if(Funcionario.getSenha() == null || Funcionario.getSenha().toString().length() > 6) {
			throw new RegraNegocioException(SENHA_INVALIDA);
		}
		
	}

}
