package com.testedelivey.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.testedelivey.model.enuns.EnumStatusPedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido", schema = "delivery")
public class Pedido {
	
	public static final String COLUNA_ID = "codigo";
	public static final String COLUNA_DESCRICAO = "descricao";
	public static final String COLUNA_VALOR = "valor";
	public static final String COLUNA_CLIENTE = "codigo_cliente";
	public static final String COLUNA_DATA_PEDIDO = "data_pedido";
	public static final String COLUNA_STATUS = "status";

	@Id 
	@Column(name=COLUNA_ID) 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column(name=COLUNA_DESCRICAO)
	private String descricao;
	
	@Column(name=COLUNA_VALOR)
	private BigDecimal valor;

	@ManyToOne
	@JoinColumn(name=COLUNA_CLIENTE)
	private Cliente usuario;
	
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@Column(name=COLUNA_DATA_PEDIDO)
	private LocalDate dataCadastro;
		
	@Column(name=COLUNA_STATUS)
	@Enumerated(value= EnumType.STRING)
	private EnumStatusPedido status;

}
