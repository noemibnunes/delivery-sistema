package com.testedelivey.api.controller;

import java.time.LocalDate;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testedelivey.api.dto.AtualizarStatusDTO;
import com.testedelivey.api.dto.PedidoDTO;
import com.testedelivey.exception.RegraNegocioException;
import com.testedelivey.model.entity.Cliente;
import com.testedelivey.model.entity.Pedido;
import com.testedelivey.model.enuns.EnumStatusPedido;
import com.testedelivey.service.implementacao.ClienteService;
import com.testedelivey.service.implementacao.PedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pedidos")
public class PedidoController {

	private final PedidoService service;

	private final ClienteService clienteService;
	
	private static final String USER_NAO_ENCONTRADO = "Usuário não encontrado para o ID informado!";
	private static final String PEDIDO_NAO_ENCONTRADO = "Pedido não encontrado!";
	private static final String PEDIDO_NAO_ATUALIZADO = "Não foi possível atualizar o status do pedido, envie um status válido!";

	@GetMapping("{id}")
	public Optional<Pedido> findById(@PathVariable("id") Long id) {
		return service.obterPorId(id);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<?> salvarPedido(@RequestBody PedidoDTO dto) {
		try {
			Pedido entity = converterDTO(dto);
			entity = service.salvarPedido(entity);
			return new ResponseEntity<>(entity, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarPedido(@PathVariable("id") Long id, @RequestBody PedidoDTO dto) {
		return service.obterPorId(id).map(entity -> {
			try {
				Pedido pedido = converterDTO(dto);
				pedido.setId(entity.getId());
				service.atualizarPedido(pedido);
				return ResponseEntity.ok(pedido);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>(PEDIDO_NAO_ENCONTRADO, HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletarPedido(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(entity -> {
			try {
				service.deletarPedido(entity);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>(PEDIDO_NAO_ENCONTRADO, HttpStatus.BAD_REQUEST));
	}

	@GetMapping("/buscar")
	public ResponseEntity<?> buscar(@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam("usuario") Long idUsuario) {

		Pedido filtro = new Pedido();
		filtro.setDescricao(descricao);
		Optional<Cliente> user = clienteService.obterPorId(idUsuario);

		if (!user.isPresent()) {
			return ResponseEntity.badRequest()
					.body("Não foi possível realizar a consulta, Usuário não encontrado para o ID informado!");
		} else {
			filtro.setUsuario(user.get());
		}

		List<Pedido> resultado = service.buscarPedido(filtro);

		return ResponseEntity.ok(resultado);
	}

	@PutMapping("/atualizarStatus/{id}")
	public ResponseEntity<?> atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizarStatusDTO statusDto) {

		return service.obterPorId(id).map(entity -> {
			EnumStatusPedido statusSelecionado = EnumStatusPedido.valueOf(statusDto.getStatus());
			if (statusSelecionado == null) {
				return ResponseEntity.badRequest()
						.body(PEDIDO_NAO_ATUALIZADO);
			}
			try {
				entity.setStatus(statusSelecionado);
				service.atualizarStatus(entity, statusSelecionado);
				return ResponseEntity.ok(entity);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>(PEDIDO_NAO_ENCONTRADO, HttpStatus.BAD_REQUEST));
	}

	private PedidoDTO converter(Pedido pedido) {
		return PedidoDTO.builder()
				.id(pedido.getId())
				.descricao(pedido.getDescricao())
				.valor(pedido.getValor())
				.status(pedido.getStatus().name())
				.usuario(pedido.getUsuario().getId())
				.build();
	}
	
	private Pedido converterDTO(PedidoDTO dto) {
		Pedido pedido = new Pedido();
		pedido.setId(dto.getId());
		pedido.setDescricao(dto.getDescricao());
		pedido.setValor(dto.getValor());
		pedido.setDataCadastro(LocalDate.now());

		Cliente user = clienteService.obterPorId(dto.getUsuario())
				.orElseThrow(() -> new RegraNegocioException(USER_NAO_ENCONTRADO));

		pedido.setUsuario(user);

		if (dto.getStatus() != null) {
			pedido.setStatus(EnumStatusPedido.valueOf(dto.getStatus()));
		}
		return pedido;
	}

}