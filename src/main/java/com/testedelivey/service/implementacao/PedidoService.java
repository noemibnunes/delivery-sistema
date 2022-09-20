package com.testedelivey.service.implementacao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.testedelivey.exception.RegraNegocioException;
import com.testedelivey.model.entity.Pedido;
import com.testedelivey.model.enuns.EnumStatusPedido;
import com.testedelivey.model.repository.PedidoRepository;
import com.testedelivey.service.IPedidoService;

@Service
public class PedidoService implements IPedidoService {

	@Autowired
	private PedidoRepository repository;
	
	public PedidoService(PedidoRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Pedido salvarPedido(Pedido pedido) {
		validarPedido(pedido);
		pedido.setStatus(EnumStatusPedido.PENDENTE);
		return repository.save(pedido);
	}

	@Override
	@Transactional
	public Pedido atualizarPedido(Pedido pedido) {
		Objects.requireNonNull(pedido.getId());
		validarPedido(pedido);
		return repository.save(pedido);
	}

	@Override
	@Transactional
	public void deletarPedido(Pedido pedido) {
		Objects.requireNonNull(pedido.getId());
		repository.delete(pedido);
	}

	@Override
	@Transactional
	public List<Pedido> buscarPedido(Pedido pedidoFiltro) {
		// leva em consideração o filtro
		Example example = Example.of(pedidoFiltro,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING)); // não importa
																											// se é
																											// maiusculo
																											// ou
																											// minisculo
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatus(Pedido pedido, EnumStatusPedido status) {
		pedido.setStatus(status);
		atualizarPedido(pedido);
	}

	@Override
	public void validarPedido(Pedido pedido) {
		if (pedido.getDescricao() == null || pedido.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma Descrição válida!");
		}

		if (pedido.getUsuario() == null || pedido.getUsuario().getId() == null) {
			throw new RegraNegocioException("Informe um Usuário!");
		}

		if (pedido.getValor() == null || pedido.getValor().compareTo(BigDecimal.ZERO) < 1) {
			throw new RegraNegocioException("Informe um Valor válido!");
		}

	}

	@Override
	public Optional<Pedido> obterPorId(Long id) {
		return repository.findById(id);
	}

}
