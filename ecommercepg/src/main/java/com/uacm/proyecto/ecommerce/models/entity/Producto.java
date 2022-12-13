package com.uacm.proyecto.ecommerce.models.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String descripcion;
	private Double precio;
	@Column(name = "num_piezas")
	private Integer numPiezas;
	private Integer estado;
	@Column(name = "informacion_adicional")
	private String informacionAdicional;
	private Double descuento;
	private Double bonificacion;

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public Double getBonificacion() {
		return bonificacion;
	}

	public void setBonificacion(Double bonificacion) {
		this.bonificacion = bonificacion;
	}

	// llave foranea vendedor_id
	@ManyToOne(fetch = FetchType.LAZY)
	private Vendedor vendedor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getNumPiezas() {
		return numPiezas;
	}

	public void setNumPiezas(Integer numPiezas) {
		this.numPiezas = numPiezas;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getInformacionAdicional() {
		return informacionAdicional;
	}

	public void setInformacionAdicional(String informacionAdicional) {
		this.informacionAdicional = informacionAdicional;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", numPiezas=" + numPiezas + ", estado=" + estado + ", informacionAdicional=" + informacionAdicional
				+ ", vendedor=" + vendedor + "]";
	}

	private static final long serialVersionUID = 1L;

}
