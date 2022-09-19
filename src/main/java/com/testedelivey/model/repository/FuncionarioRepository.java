package com.testedelivey.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testedelivey.model.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	Optional<Funcionario> findByNome(String nome); 

}
