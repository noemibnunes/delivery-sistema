package com.testedelivey.api.dto;

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
public class EntregaDTO {
	
	private Long id;
	private Long pedido;
	private Long funcionario;
	private String status;

}