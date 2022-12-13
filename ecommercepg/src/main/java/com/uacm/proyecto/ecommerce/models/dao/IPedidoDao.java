package com.uacm.proyecto.ecommerce.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.uacm.proyecto.ecommerce.models.entity.Pedido;

public interface IPedidoDao extends PagingAndSortingRepository<Pedido, Long>{

}
