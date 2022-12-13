package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uacm.proyecto.ecommerce.models.dao.IProductoDao;
import com.uacm.proyecto.ecommerce.models.entity.Producto;

@Service
public class ProductoService implements IProductoService 
{
	@Autowired
	IProductoDao productoDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Producto> obtenerTodos() {
		
		return (List<Producto>)productoDao.findAll();
	}

	@Override
	@Transactional
	public Producto guardar(Producto producto) 
	{
		
		return productoDao.save(producto);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Producto buscar(Long id) {
		
		return productoDao.findById(id).orElse(null);
	}

	@Override
	public Page<Producto> obtenerTodosPagina(Pageable pageable) 
	{
		return productoDao.findAll(pageable);
	}

	@Override
	public void eliminar(Long id) 
	{		
		
		productoDao.deleteById(id);
		
	}

}
