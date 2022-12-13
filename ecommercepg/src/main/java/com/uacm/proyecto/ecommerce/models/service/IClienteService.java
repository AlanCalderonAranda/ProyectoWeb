package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uacm.proyecto.ecommerce.models.entity.Cliente;

public interface IClienteService 
{
	
	public List<Cliente> obtenerTodos();
	
	public Cliente guardar(Cliente cliente);
	
	public Cliente buscar(Long id);
	
	public void eliminar(Long id);
	
	Page<Cliente> obtenerTodosPagina(Pageable pageable);
	
}
