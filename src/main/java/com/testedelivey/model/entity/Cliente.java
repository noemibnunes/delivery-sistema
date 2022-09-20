package com.testedelivey.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente", schema = "delivery")
public class Cliente {
	
	public static final String COLUNA_ID = "codigo";
	public static final String COLUNA_NOME = "nome";
	public static final String COLUNA_EMAIL = "email";
	public static final String COLUNA_TELEFONE = "telefone";
	public static final String COLUNA_ENDERECO = "endereco";
	public static final String COLUNA_SENHA = "senha";

	@Id 
	@Column(name=COLUNA_ID) 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id; 
	
	@Column(name=COLUNA_NOME)
	private String nome;
	
	@Column(name=COLUNA_EMAIL)
	private String email;
	
	@Column(name=COLUNA_TELEFONE)
	private String telefone;

	@Column(name=COLUNA_ENDERECO)
	private String endereco;
	
	@Column(name=COLUNA_SENHA)
	@JsonIgnore
	private String senha;
}