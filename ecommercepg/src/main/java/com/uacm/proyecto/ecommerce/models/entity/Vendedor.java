package com.uacm.proyecto.ecommerce.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.uacm.proyecto.ecommerce.models.entity.util.Usuario;
//las entity deben implementar Serializable

@Entity
@Table(name = "vendedores")
public class Vendedor extends Usuario implements Serializable {

	//Se ejecuta antes de insertar un Vendedor a la bd
	@PrePersist
	public void prePersist() {
		this.setFechaRegistro(new Date());
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vendedor", cascade = CascadeType.ALL)
	private List<Producto> productos;

	public List<Producto> getProductos() {
		
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Vendedor [productos=" + productos + ", toString()=" + super.toString() + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3465585371919435205L;

}
