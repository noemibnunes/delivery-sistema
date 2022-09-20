package com.testedelivey.model.entity;

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

import com.testedelivey.model.enuns.EnumStatusEntrega;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entrega", schema = "delivery")
public class Entrega {
	
	public static final String COLUNA_ID = "codigo";
	public static final String COLUNA_PEDIDO = "codigo_pedido";
	public static final String COLUNA_FUNCIONARIO = "codigo_funcionario";
	public static final String COLUNA_DATA_ENTREGA = "data_entrega";
	public static final String COLUNA_STATUS = "status";

	@Id 
	@Column(name=COLUNA_ID) 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@ManyToOne
	@JoinColumn(name=COLUNA_PEDIDO)
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name=COLUNA_FUNCIONARIO)
	private Funcionario funcionario;
	
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@Column(name=COLUNA_DATA_ENTREGA)
	private LocalDate dataEntrega;
		
	@Column(name=COLUNA_STATUS)
	@Enumerated(value= EnumType.STRING)
	private EnumStatusEntrega status;

}
