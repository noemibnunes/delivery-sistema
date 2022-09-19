package com.testedelivey.service;

import java.util.Optional;

import com.testedelivey.model.EnumStatusEntrega;
import com.testedelivey.model.entity.Entrega;

public interface IEntregaService {

	Entrega salvarEntrega(Entrega entrega);
	
	Entrega atualizarEntrega(Entrega entrega);
	
	void deletarEntrega(Entrega entrega);
	
	void atualizarStatus(Entrega entrega, EnumStatusEntrega status);
	
	void validarEntrega(Entrega entrega);
	
	Optional<Entrega> obterPorId(Long id);
}
