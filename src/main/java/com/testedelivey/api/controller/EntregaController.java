package com.testedelivey.api.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testedelivey.api.dto.AtualizarStatusDTO;
import com.testedelivey.api.dto.EntregaDTO;
import com.testedelivey.exception.RegraNegocioException;
import com.testedelivey.model.entity.Entrega;
import com.testedelivey.model.entity.Funcionario;
import com.testedelivey.model.entity.Pedido;
import com.testedelivey.model.enuns.EnumStatusEntrega;
import com.testedelivey.service.implementacao.EntregaService;
import com.testedelivey.service.implementacao.FuncionarioService;
import com.testedelivey.service.implementacao.PedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/entregas")
public class EntregaController {

	private final EntregaService service;

	private final FuncionarioService funcionarioService;
	
	private final PedidoService pedidoService;

	
	private static final String USER_NAO_ENCONTRADO = "Usuário não encontrado para o ID informado!";
	private static final String ENTREGA_NAO_ENCONTRADA = "Entrega não encontrado!";
	private static final String PEDIDO_NAO_ENCONTRADO = "Pedido não encontrado!";
	private static final String ENTREGA_NAO_ATUALIZADA = "Não foi possível atualizar o status da entrega, envie um status válido!";

	@GetMapping("{id}")
	public Optional<Entrega> findById(@PathVariable("id") Long id) {
		return service.obterPorId(id);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<?> salvarEntrega(@RequestBody EntregaDTO dto) {
		try {
			Entrega entity = converterDTO(dto);
			entity = service.salvarEntrega(entity);
			return new ResponseEntity<>(entity, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarEntrega(@PathVariable("id") Long id, @RequestBody EntregaDTO dto) {
		return service.obterPorId(id).map(entity -> {
			try {
				Entrega entrega = converterDTO(dto);
				entrega.setId(entity.getId());
				service.atualizarEntrega(entrega);
				return ResponseEntity.ok(entrega);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>(ENTREGA_NAO_ENCONTRADA, HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletarEntrega(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(entity -> {
			try {
				service.deletarEntrega(entity);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>(ENTREGA_NAO_ENCONTRADA, HttpStatus.BAD_REQUEST));
	}

	@PutMapping("/atualizarStatus/{id}")
	public ResponseEntity<?> atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizarStatusDTO statusDto) {
		return service.obterPorId(id).map(entity -> {
			EnumStatusEntrega statusSelecionado = EnumStatusEntrega.valueOf(statusDto.getStatus());
			if (statusSelecionado == null) {
				return ResponseEntity.badRequest()
						.body(ENTREGA_NAO_ATUALIZADA);
			}
			try {
				entity.setStatus(statusSelecionado);
				service.atualizarStatus(entity, statusSelecionado);
				return ResponseEntity.ok(entity);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>(ENTREGA_NAO_ATUALIZADA, HttpStatus.BAD_REQUEST));
	}

	private Entrega converterDTO(EntregaDTO dto) {
		Entrega entrega = new Entrega();
		entrega.setId(dto.getId());
		entrega.setDataEntrega(LocalDate.now());

		Funcionario user = funcionarioService.obterPorId(dto.getFuncionario())
				.orElseThrow(() -> new RegraNegocioException(USER_NAO_ENCONTRADO));
		
		Pedido pedido = pedidoService.obterPorId(dto.getPedido())
				.orElseThrow(() -> new RegraNegocioException(PEDIDO_NAO_ENCONTRADO));

		entrega.setFuncionario(user);
		entrega.setPedido(pedido);

		if (dto.getStatus() != null) {
			entrega.setStatus(EnumStatusEntrega.valueOf(dto.getStatus()));
		}
		return entrega;
	}

}