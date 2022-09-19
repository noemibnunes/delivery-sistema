package com.testedelivey.api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
	
	private Long id;
	private String descricao;
	private BigDecimal valor;
	private Long usuario;
	private String status;

}