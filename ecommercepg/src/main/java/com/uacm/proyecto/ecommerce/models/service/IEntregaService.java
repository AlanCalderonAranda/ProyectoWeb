package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uacm.proyecto.ecommerce.models.entity.Entrega;

public interface IEntregaService 
{
	
	public List<Entrega> obtenerTodas();
	
	public Entrega guardar(Entrega entrega);
	
	public Entrega buscar(Long id);
	
	public void eliminar(Long id);
	
	Page<Entrega> obtenerTodosPagina(Pageable pageable);
	
}
