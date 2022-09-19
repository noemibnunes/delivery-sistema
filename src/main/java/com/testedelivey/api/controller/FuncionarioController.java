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

import com.testedelivey.api.dto.FuncionarioDTO;
import com.testedelivey.exception.AutenticacaoException;
import com.testedelivey.exception.RegraNegocioException;
import com.testedelivey.model.entity.Funcionario;
import com.testedelivey.service.implementacao.FuncionarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/funcionarios")
public class FuncionarioController {
	
	private static final String USER_NAO_ENCONTRADO = "Usuário não encontrado!";

	private final FuncionarioService service;
	
	@GetMapping()
	public List<Funcionario> findAll() {
		return service.obterPorTodos();
	}

	@GetMapping("{id}")
	public Optional<Funcionario> findById(@PathVariable("id") Long id) {
		return service.obterPorId(id);
	}

	@PostMapping("/salvar")
	public ResponseEntity<?> salvar(@RequestBody FuncionarioDTO dto) {
		try {
			Funcionario entity = converterDTO(dto);
			entity = service.cadastrarFuncionario(entity);
			return new ResponseEntity<>(entity, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/autenticar")
	public ResponseEntity<?> autenticar(@RequestBody FuncionarioDTO dto) {
		try {
			Funcionario userAutenticado = service.autenticarFuncionario(dto.getNome(), dto.getSenha().trim());
			return ResponseEntity.ok(userAutenticado);
		}catch (AutenticacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarFuncionario(@PathVariable("id") Long id, @RequestBody FuncionarioDTO dto) {
		return service.obterPorId(id).map(entity -> {
			try {
				Funcionario Funcionario = converterDTO(dto);
				Funcionario.setId(entity.getId());
				service.atualizarFuncionario(Funcionario);
				return ResponseEntity.ok(Funcionario);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>(USER_NAO_ENCONTRADO, HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletarFuncionario(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(entity -> {
			try {
				service.deletarFuncionario(entity);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>(USER_NAO_ENCONTRADO, HttpStatus.BAD_REQUEST));
	}

	private Funcionario converterDTO(FuncionarioDTO dto) {
		Funcionario Funcionario = new Funcionario();

		Funcionario.setNome(dto.getNome().trim());
		Funcionario.setSenha(dto.getSenha().trim());

		return Funcionario;
	}
}