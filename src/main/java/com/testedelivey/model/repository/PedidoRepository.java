package com.testedelivey.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testedelivey.model.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}