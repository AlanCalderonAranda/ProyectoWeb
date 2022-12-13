package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uacm.proyecto.ecommerce.models.entity.PedidoProducto;
import com.uacm.proyecto.ecommerce.models.entity.Producto;

public interface IPedidoProductoService {
	public List<PedidoProducto> obtenerTodos();
	public PedidoProducto guardar(PedidoProducto PedidoProducto);
	public void eliminar(Long id);
	public Page<PedidoProducto> obtenerTodosPagina(Pageable pageable);
}