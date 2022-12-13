package com.uacm.proyecto.ecommerce.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.uacm.proyecto.ecommerce.models.entity.Rol;


public interface IRolDao extends CrudRepository<Rol, Long>
{

}
