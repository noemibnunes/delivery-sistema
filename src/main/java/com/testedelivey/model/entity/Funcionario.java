package com.testedelivey.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "funcionario", schema = "delivery")
public class Funcionario {
	
	public static final String COLUNA_ID = "codigo";
	public static final String COLUNA_NOME = "nome";
	public static final String COLUNA_SENHA = "senha";

	@Id 
	@Column(name=COLUNA_ID) 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id; 
	
	@Column(name=COLUNA_NOME)
	private String nome;
	
	@Column(name=COLUNA_SENHA)
	private String senha;
}