package com.testedelivey.service;

import java.util.List;
import java.util.Optional;

import com.testedelivey.model.EnumStatusPedido;
import com.testedelivey.model.entity.Pedido;

public interface IPedidoService {
	
	Pedido salvarPedido(Pedido pedido);
	Pedido atualizarPedido(Pedido pedido);
	void deletarPedido(Pedido pedido);
	List<Pedido> buscarPedido(Pedido pedidoFiltro);
	void atualizarStatus(Pedido pedido, EnumStatusPedido status);
	void validarPedido(Pedido pedido);
	Optional<Pedido> obterPorId(Long id);

}
