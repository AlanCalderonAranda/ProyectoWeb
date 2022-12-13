package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uacm.proyecto.ecommerce.models.entity.Pedido;

public interface IPedidoService 
{
	
	public List<Pedido> obtenerTodos();
	
	public Pedido guardar(Pedido pedido);
	
	public Pedido buscar(Long id);
	
	public void eliminar(Long id);
	
	public Page<Pedido> obtenerTodosPagina(Pageable pageable);

}
