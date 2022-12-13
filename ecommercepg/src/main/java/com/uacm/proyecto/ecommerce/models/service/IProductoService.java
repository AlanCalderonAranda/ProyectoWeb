package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uacm.proyecto.ecommerce.models.entity.Producto;

public interface IProductoService {
	public List<Producto> obtenerTodos();
	public Producto guardar(Producto producto);
	public Producto buscar(Long id);
	public void eliminar(Long id);	
	public Page<Producto> obtenerTodosPagina(Pageable pageable);
}
