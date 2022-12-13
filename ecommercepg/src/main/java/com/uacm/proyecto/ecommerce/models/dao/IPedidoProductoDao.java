package com.uacm.proyecto.ecommerce.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.uacm.proyecto.ecommerce.models.entity.PedidoProducto;

public interface IPedidoProductoDao extends PagingAndSortingRepository<PedidoProducto, Long>{

}
