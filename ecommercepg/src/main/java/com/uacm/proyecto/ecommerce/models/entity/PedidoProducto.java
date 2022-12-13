package com.uacm.proyecto.ecommerce.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="pedidos_productos")
public class PedidoProducto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "subtotal_pagar")
	private Double subtotalPagar;
	@Column(name = "info_adicional")
	private String infoAdicional;
	@ManyToOne
	@JoinColumn(name="pedido_id")
	private Pedido pedido;
	@ManyToOne
	@JoinColumn(name="producto_id")
	private Producto producto;
	
	public PedidoProducto() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getSubtotalPagar() {
		return subtotalPagar;
	}

	public void setSubtotalPagar(Double subtotalPagar) {
		this.subtotalPagar = subtotalPagar;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;

}
