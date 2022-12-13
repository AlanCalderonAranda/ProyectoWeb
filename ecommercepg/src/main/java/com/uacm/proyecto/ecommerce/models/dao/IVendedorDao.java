package com.uacm.proyecto.ecommerce.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.uacm.proyecto.ecommerce.models.entity.Vendedor;
//ofrece operaciones a la base de datos
public interface IVendedorDao extends PagingAndSortingRepository<Vendedor, Long>{
//ya existen los metodos de CrudRepository
	
	
}
