package com.testedelivey.service.implementacao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testedelivey.exception.AutenticacaoException;
import com.testedelivey.exception.RegraNegocioException;
import com.testedelivey.model.entity.Cliente;
import com.testedelivey.model.repository.ClienteRepository;
import com.testedelivey.service.IClienteService;

@Service
public class ClienteService implements IClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	public ClienteService(ClienteRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Optional<Cliente> obterPorId(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public List<Cliente> obterPorTodos() {
		return repository.findAll();
	}
	
	@Transactional
	public Cliente cadastrarUsuario(Cliente cliente) {
		validarEmail(cliente.getEmail());
		return repository.save(cliente);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		
		if(existe) {
			throw new RegraNegocioException("Email já cadastrado!");
		}
	}
	
	@Override
	public Cliente autenticarUsuario(String email, String senha) {
		
		Optional<Cliente> usuario = repository.findByEmail(email); 
		
		if(!usuario.isPresent()) {
			throw new AutenticacaoException("Usuário não encontrado para o email informado!");
		}
		
		if(!senha.trim().equals(usuario.get().getSenha().trim())){
			throw new AutenticacaoException("Senha Inválida!");
		}
		
		return usuario.get();
	}
	
	@Override
	@Transactional
	public Cliente atualizarCliente(Cliente cliente) {
		Objects.requireNonNull(cliente.getId());
		validarUsuario(cliente);
		return repository.save(cliente);
	}

	@Override
	@Transactional
	public void deletarCliente(Cliente cliente) {
		Objects.requireNonNull(cliente.getId());
		repository.delete(cliente);
	}
	
	@Override
	public void validarUsuario(Cliente cliente) {
		if(cliente.getNome() == null || cliente.getNome().trim().equals("")) {
			throw new RegraNegocioException("Informe um Nome válido!");
		}
		
		if(cliente.getEndereco() == null) {
			throw new RegraNegocioException("Informe um Endereço!");
		}
		
		if(cliente.getTelefone() == null || cliente.getTelefone().matches("[0-9]*")) {
			throw new RegraNegocioException("Informe um Telefone!");
		}
		
		if(cliente.getSenha() == null || cliente.getSenha().toString().length() > 6) {
			throw new RegraNegocioException("Informe uma Senha válida!");
		}
		
		if(cliente.getEmail() == null) {
			throw new RegraNegocioException("Informe um Email!");
		}
		
		if(cliente.getEmail() != null) {
			if(!isEmailValido(cliente.getEmail())){
				throw new RegraNegocioException("Informe um Email válido!");
			}
		}
	}
	
	public static boolean isEmailValido(String email) {
        boolean isEmailValido = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
            	isEmailValido = true;
            }
        }
        return isEmailValido;
    }


}
