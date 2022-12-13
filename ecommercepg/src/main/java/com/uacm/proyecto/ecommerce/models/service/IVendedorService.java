package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uacm.proyecto.ecommerce.models.entity.Vendedor;

//operaciones a bd CRUD
public interface IVendedorService {
	//consulta todos los vendedores de la bd
	public List<Vendedor> obtenerTodos();
	//inserta un vendedor en la bd
	public Vendedor guardar(Vendedor vendedor);
	public Vendedor buscar(Long id);
	
	public void eliminar(Long id);		
	
	public Page<Vendedor> obtenerTodosPagina(Pageable pageable);
	
}
