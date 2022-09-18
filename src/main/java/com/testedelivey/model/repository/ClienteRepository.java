package com.testedelivey.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testedelivey.model.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>  {

	// SELECT * FROM CLIENTES WHERE EXISTS (email)
		boolean existsByEmail(String email);
		
		Optional<Cliente> findByEmail(String email); 
}