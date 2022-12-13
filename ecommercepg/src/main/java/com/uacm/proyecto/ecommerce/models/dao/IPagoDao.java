package com.uacm.proyecto.ecommerce.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.uacm.proyecto.ecommerce.models.entity.Pago;

public interface IPagoDao extends PagingAndSortingRepository<Pago, Long>
{

}
