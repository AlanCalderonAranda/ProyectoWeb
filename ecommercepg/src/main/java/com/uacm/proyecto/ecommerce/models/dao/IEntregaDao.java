package com.uacm.proyecto.ecommerce.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.uacm.proyecto.ecommerce.models.entity.Entrega;

public interface IEntregaDao extends PagingAndSortingRepository<Entrega, Long>
{

}
