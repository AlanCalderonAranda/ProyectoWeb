package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import com.uacm.proyecto.ecommerce.models.entity.Rol;

public interface IRolService 
{
	
	public List<Rol> obtenerTodos();
	
	public Rol guardar(Rol rol);
	
	public Rol buscar(Long id);

}
