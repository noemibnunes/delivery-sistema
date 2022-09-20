package com.testedelivey.service;

import com.testedelivey.model.entity.Cliente;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

public interface IJwtService {
	
	String gerarToken(Cliente cliente);
	
	Claims obterClaims(String token) throws ExpiredJwtException;

	boolean isTokenValido(String token);
	
	String obterLoginUsuario(String token);
}
