package com.uacm.proyecto.ecommerce.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Temporal(TemporalType.DATE)
	@Column(name = "fecha_pedido")
	private LocalDateTime fechaPedido;
	private Integer estado;
	@Column(name = "total_pagar")
	private Double totalPagar;
	@Column(name = "info_adicional")
	private String infoAdicional;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "FK_CLIENTE_ID"))
	private Cliente cliente;
	
	// mappedBy nombre de la propiedad en PedidoProducto
	@OneToMany(mappedBy = "pedido")
	private List<PedidoProducto> pedidosProductos;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pago_id", foreignKey = @ForeignKey(name = "FK_PAGO_ID"))
	private Pago pago;

	@PrePersist
	public void prePersist() {
		this.fechaPedido = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(LocalDateTime fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Double getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(Double totalPagar) {
		this.totalPagar = totalPagar;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<PedidoProducto> getPedidosProductos() {
		return pedidosProductos;
	}

	public void setPedidosProductos(List<PedidoProducto> pedidosProductos) {
		this.pedidosProductos = pedidosProductos;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", fechaPedido=" + fechaPedido + ", estado=" + estado + ", totalPagar=" + totalPagar
				+ ", infoAdicional=" + infoAdicional + ", cliente=" + cliente + ", pedidosProductos=" + pedidosProductos
				+ "]";
	}

	private static final long serialVersionUID = 1L;
}