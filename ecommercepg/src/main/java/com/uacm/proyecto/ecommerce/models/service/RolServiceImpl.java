package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uacm.proyecto.ecommerce.models.dao.IRolDao;
import com.uacm.proyecto.ecommerce.models.entity.Rol;

@Service
public class RolServiceImpl implements IRolService
{

	@Autowired
	IRolDao rolDao;
	
	@Override
	public List<Rol> obtenerTodos() 
	{		
		return (List<Rol>)rolDao.findAll();
	}

	@Override
	public Rol guardar(Rol rol) 
	{
		
		return rolDao.save(rol);
	}

	@Override
	public Rol buscar(Long id) 
	{		
		return rolDao.findById(id).orElse(null);
	}
	
	

}
