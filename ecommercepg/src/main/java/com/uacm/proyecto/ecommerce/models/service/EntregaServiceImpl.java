package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uacm.proyecto.ecommerce.models.dao.IEntregaDao;
import com.uacm.proyecto.ecommerce.models.entity.Entrega;

@Service
public class EntregaServiceImpl implements IEntregaService
{

	@Autowired
	IEntregaDao entregaDao;
	
	@Override
	public List<Entrega> obtenerTodas() 
	{
		
		return (List<Entrega>)entregaDao.findAll();
	}

	@Override
	public Entrega guardar(Entrega entrega) 
	{
	
		return entregaDao.save(entrega);
	}

	@Override
	public Entrega buscar(Long id) 
	{
		
		return entregaDao.findById(id).orElse(null);
	}

	@Override
	public Page<Entrega> obtenerTodosPagina(Pageable pageable) 
	{
		
		return entregaDao.findAll(pageable);
	}

	@Override
	public void eliminar(Long id) 
	{
		
		entregaDao.deleteById(id);
		
	}

}
