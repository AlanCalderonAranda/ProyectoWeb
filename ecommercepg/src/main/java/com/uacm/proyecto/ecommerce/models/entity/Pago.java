package com.uacm.proyecto.ecommerce.models.entity;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pagos")
public class Pago implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "numero_tarjeta")
	private String numTarjeta;

	@Column(name = "fecha_expiracion")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM")
	private Date fechaExpiracion;
	private String titular;
	private String CCV;

	// UN PAGO SE ASIGNAN A UN CLIENTE
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "FK_CLIENTE_ID"))
	private Cliente cliente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumTarjeta() {
		return numTarjeta;
	}

	public void setNumTarjeta(String numTarjeta) {
		this.numTarjeta = numTarjeta;
	}

	public Date getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(Date fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getCCV() {
		return CCV;
	}

	public void setCCV(String cCV) {
		CCV = cCV;
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

	private static final long serialVersionUID = -1393057026274375255L;

}
