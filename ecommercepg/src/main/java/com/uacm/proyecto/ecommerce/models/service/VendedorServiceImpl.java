package com.uacm.proyecto.ecommerce.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uacm.proyecto.ecommerce.models.dao.IVendedorDao;
import com.uacm.proyecto.ecommerce.models.entity.Vendedor;
//@Service le dice a spring que trate la clase de manera especifica
//para hacer consultas a la bd usando la interface CRUDREPOSITORY

@Service
public class VendedorServiceImpl implements IVendedorService{
	//usamos CRUDREPOSITORY
	@Autowired
	IVendedorDao vendedorDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Vendedor> obtenerTodos() {
		
		return (List<Vendedor>)vendedorDao.findAll();
	}

	@Override
	@Transactional
	public Vendedor guardar(Vendedor vendedor) 
	{		
		return vendedorDao.save(vendedor);
	}
	
	

	@Override
	@Transactional
	public Vendedor buscar(Long id) {
	
		return vendedorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Page<Vendedor> obtenerTodosPagina(Pageable pageable) {
		
		return vendedorDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void eliminar(Long id) 
	{
		vendedorDao.deleteById(id);
	}

}
