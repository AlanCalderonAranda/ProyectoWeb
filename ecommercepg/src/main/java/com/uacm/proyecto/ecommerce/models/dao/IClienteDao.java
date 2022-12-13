package com.uacm.proyecto.ecommerce.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.uacm.proyecto.ecommerce.models.entity.Cliente;


public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>
{

}
