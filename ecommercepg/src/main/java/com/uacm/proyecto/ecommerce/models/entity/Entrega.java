package com.uacm.proyecto.ecommerce.models.entity;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "entregas")
public class Entrega implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "fecha_entrega")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fechaEntrega;
	
	@Column(name = "hora_entrega")
	private LocalTime horaEntrega;
	@Column(name = "punto_entrega")
	private String puntoEntrega;
	
	@Column(name = "notas_adicionales")
	private String notasAdicionales;
	private Integer intentos;
	private boolean estado;
	
	// UN PEDIDO SE ASIGNAN A UNA ENTERGA
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pedido_id", foreignKey = @ForeignKey(name = "FK_PEDIDO_ID"))
	private Pedido pedido;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	

	public LocalTime getHoraEntrega() {
		return horaEntrega;
	}

	public void setHoraEntrega(LocalTime horaEntrega) {
		this.horaEntrega = horaEntrega;
	}

	public String getPuntoEntrega() {
		return puntoEntrega;
	}

	public void setPuntoEntrega(String puntoEntrega) {
		this.puntoEntrega = puntoEntrega;
	}

	public String getNotasAdicionales() {
		return notasAdicionales;
	}

	public void setNotasAdicionales(String notasAdicionales) {
		this.notasAdicionales = notasAdicionales;
	}

	public Integer getIntentos() {
		return intentos;
	}

	public void setIntentos(Integer intentos) {
		this.intentos = intentos;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Entrega [id=" + id + ", fechaEntrega=" + fechaEntrega + ", horaEntrega=" + horaEntrega
				+ ", puntoEntrega=" + puntoEntrega + ", notasAdicionales=" + notasAdicionales + ", intentos=" + intentos
				+ ", estado=" + estado + ", pedido=" + pedido + "]";
	}
	private static final long serialVersionUID = 904265780985794867L;
}
