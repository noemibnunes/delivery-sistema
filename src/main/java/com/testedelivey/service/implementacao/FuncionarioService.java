package com.testedelivey.service.implementacao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	private PasswordEncoder encoder;
	
	public FuncionarioService(FuncionarioRepository repository, PasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
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
	public Funcionario cadastrarFuncionario(Funcionario funcionario) {
		validarFuncionario(funcionario);
		criptografarSenha(funcionario);
		return repository.save(funcionario);
	}
	
	private void criptografarSenha(Funcionario funcionario) { 
		String senha = funcionario.getSenha();
		String senhaCripto = encoder.encode(senha);
		funcionario.setSenha(senhaCripto);
	}

	@Override
	public Funcionario autenticarFuncionario(String nome, String senha) {
		
		Optional<Funcionario> usuario = repository.findByNome(nome); 
		
		if(!usuario.isPresent()) {
			throw new AutenticacaoException(USER_NAO_ENCONTRADO);
		}
		
		boolean senhasIguais = encoder.matches(senha, usuario.get().getSenha());

		if (!senhasIguais) {
			throw new AutenticacaoException(SENHA_INCORRETA);
		}
		
		return usuario.get();
	}
	
	@Override
	@Transactional
	public Funcionario atualizarFuncionario(Funcionario funcionario) {
		Objects.requireNonNull(funcionario.getId());
		validarFuncionario(funcionario);
		return repository.save(funcionario);
	}

	@Override
	@Transactional
	public void deletarFuncionario(Funcionario funcionario) {
		Objects.requireNonNull(funcionario.getId());
		repository.delete(funcionario);
	}
	
	@Override
	public void validarFuncionario(Funcionario funcionario) {
		if(funcionario.getNome() == null || funcionario.getNome().trim().equals("")) {
			throw new RegraNegocioException(NOME_INVALIDO);
		}
		
		if(funcionario.getSenha() == null || funcionario.getSenha().toString().length() > 6) {
			throw new RegraNegocioException(SENHA_INVALIDA);
		}
		
	}

}
