package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uacm.proyecto.ecommerce.models.dao.IPedidoProductoDao;
import com.uacm.proyecto.ecommerce.models.entity.PedidoProducto;
import com.uacm.proyecto.ecommerce.models.entity.Producto;


@Service
public class PedidoProductoServiceImpl implements IPedidoProductoService{
	
	// Usamos el CRUD Repository
		@Autowired
		IPedidoProductoDao pedidoProductoDao;

	@Override
	@Transactional(readOnly = true)
	public List<PedidoProducto> obtenerTodos() {
		return (List<PedidoProducto>)pedidoProductoDao.findAll();
	}

	@Override
	public PedidoProducto guardar(PedidoProducto PedidoProducto) {
		return pedidoProductoDao.save(PedidoProducto);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<PedidoProducto> obtenerTodosPagina(Pageable pageable){
		return pedidoProductoDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		pedidoProductoDao.deleteById(id);
	}
	

}
