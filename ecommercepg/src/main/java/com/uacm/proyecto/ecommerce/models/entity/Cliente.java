package com.uacm.proyecto.ecommerce.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.uacm.proyecto.ecommerce.models.entity.util.Usuario;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario implements Serializable {
	@Column(name = "monedero")
	private Double monedero = 0.0;

	@PrePersist
	public void prePersist() {
		this.setFechaRegistro(new Date());
	}

	/* mappedBy hace relacion en atributo de PEDIDO colocar su propiedad */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Pedido> pedidos;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Pago> pagos;

	public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	// PARA EL MONEDERO
	public Double getMonedero() {
		return monedero;
	}

	public void setMonedero(Double monedero) {
		this.monedero = monedero;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Cliente [pedidos=" + pedidos + ",  toString()=" + super.toString() + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
