package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uacm.proyecto.ecommerce.models.dao.IPagoDao;
import com.uacm.proyecto.ecommerce.models.entity.Pago;

@Service
public class PagoServiceImpl implements IPagoService 
{

	@Autowired
	IPagoDao pagoDao;
	
	@Override
	public List<Pago> obtenerTodos() 
	{
		
		return (List<Pago>)pagoDao.findAll();
	}

	@Override
	public Pago guardar(Pago pago) 
	{
		
		return pagoDao.save(pago);
	}

	@Override
	public Pago buscar(Long id) 
	{	
		
		return pagoDao.findById(id).orElse(null);
	}

	@Override
	public Page<Pago> obtenerTodosPagina(Pageable pageable) 
	{
		
		return pagoDao.findAll(pageable);
	}

	@Override
	public void eliminar(Long id) 
	{
		pagoDao.deleteById(id);
		
	}

}
