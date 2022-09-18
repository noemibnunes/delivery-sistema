package com.testedelivey.api.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.testedelivey.api.dto.ClienteDTO;
import com.testedelivey.exception.AutenticacaoException;
import com.testedelivey.exception.RegraNegocioException;
import com.testedelivey.model.entity.Cliente;
import com.testedelivey.service.implementacao.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clientes")
public class ClienteController {
	
	private static final String USER_NAO_ENCONTRADO = "Usuário não encontrado!";

	private final ClienteService service;
	
	@GetMapping()
	public List<Cliente> findAll() {
		return service.obterPorTodos();
	}

	@GetMapping("{id}")
	public Optional<Cliente> findById(@PathVariable("id") Long id) {
		return service.obterPorId(id);
	}

	@PostMapping("/salvar")
	public ResponseEntity<?> salvar(@RequestBody ClienteDTO dto) {
		try {
			Cliente entity = converterDTO(dto);
			entity = service.cadastrarUsuario(entity);
			return new ResponseEntity<>(entity, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/autenticar")
	public ResponseEntity<?> autenticar(@RequestBody ClienteDTO dto) {
		try {
			Cliente userAutenticado = service.autenticarUsuario(dto.getEmail(), dto.getSenha().trim());
			return ResponseEntity.ok(userAutenticado);
		}catch (AutenticacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarCliente(@PathVariable("id") Long id, @RequestBody ClienteDTO dto) {
		return service.obterPorId(id).map(entity -> {
			try {
				Cliente cliente = converterDTO(dto);
				cliente.setId(entity.getId());
				service.atualizarCliente(cliente);
				return ResponseEntity.ok(cliente);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>(USER_NAO_ENCONTRADO, HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletarCliente(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(entity -> {
			try {
				service.deletarCliente(entity);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>(USER_NAO_ENCONTRADO, HttpStatus.BAD_REQUEST));
	}

	private Cliente converterDTO(ClienteDTO dto) {
		Cliente cliente = new Cliente();

		cliente.setNome(dto.getNome().trim());
		cliente.setEmail(dto.getEmail().trim());
		cliente.setTelefone(dto.getTelefone().trim());
		cliente.setEndereco(dto.getEndereco().trim());
		cliente.setSenha(dto.getSenha().trim());

		return cliente;
	}
}