package com.uacm.proyecto.ecommerce.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.uacm.proyecto.ecommerce.models.entity.Producto;


public interface IProductoDao extends PagingAndSortingRepository<Producto, Long> {

}
