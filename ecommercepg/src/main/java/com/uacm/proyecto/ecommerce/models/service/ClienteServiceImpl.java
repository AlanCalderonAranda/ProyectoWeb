package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uacm.proyecto.ecommerce.models.dao.IClienteDao;
import com.uacm.proyecto.ecommerce.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService
{

	@Autowired
	IClienteDao clienteDao;
	
	@Override
	public List<Cliente> obtenerTodos() 
	{		
		return (List<Cliente>)clienteDao.findAll();
	}

	@Override
	public Cliente guardar(Cliente cliente) 
	{
		
		return clienteDao.save(cliente);
	}

	@Override
	public Cliente buscar(Long id) 
	{
		
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id) 
	{
		
		clienteDao.deleteById(id);
	}
	
	@Override
	@Transactional
	public Page<Cliente> obtenerTodosPagina(Pageable pageable) 
	{
		
		return clienteDao.findAll(pageable);
	}
	
	

}
