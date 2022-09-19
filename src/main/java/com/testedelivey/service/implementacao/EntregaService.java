package com.testedelivey.service.implementacao;

import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testedelivey.exception.RegraNegocioException;
import com.testedelivey.model.EnumStatusEntrega;
import com.testedelivey.model.entity.Entrega;
import com.testedelivey.model.repository.EntregaRepository;
import com.testedelivey.service.IEntregaService;

@Service
public class EntregaService implements IEntregaService{
	
	@Autowired
	private EntregaRepository repository;
	
	public EntregaService(EntregaRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Entrega salvarEntrega(Entrega entrega) {
		validarEntrega(entrega);
		entrega.setStatus(EnumStatusEntrega.CAMINHO);
		return repository.save(entrega);
	}

	@Override
	@Transactional
	public Entrega atualizarEntrega(Entrega entrega) {
		Objects.requireNonNull(entrega.getId());
		validarEntrega(entrega);
		return repository.save(entrega);
	}

	@Override
	@Transactional
	public void deletarEntrega(Entrega entrega) {
		Objects.requireNonNull(entrega.getId());
		repository.delete(entrega);
	}

	@Override
	public void atualizarStatus(Entrega entrega, EnumStatusEntrega status) {
		entrega.setStatus(status);
		atualizarEntrega(entrega);
	}

	@Override
	public void validarEntrega(Entrega entrega) {
		if (entrega.getPedido() == null || entrega.getPedido().getId() == null) {
			throw new RegraNegocioException("Informe um Pedido!");
		}

		if (entrega.getFuncionario() == null || entrega.getFuncionario().getId() == null) {
			throw new RegraNegocioException("Informe um Funcionário!");
		}

	}

	@Override
	public Optional<Entrega> obterPorId(Long id) {
		return repository.findById(id);
	}


}
