package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uacm.proyecto.ecommerce.models.entity.Pago;

public interface IPagoService 
{

	public List<Pago> obtenerTodos();
	
	public Pago guardar(Pago pago);
	
	public Pago buscar(Long id);
	
	public Page<Pago> obtenerTodosPagina(Pageable pageable);
	
	public void eliminar(Long id);
	
}
