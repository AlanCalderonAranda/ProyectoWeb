package com.uacm.proyecto.ecommerce.models.entity.util;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String appaterno;
	private String apmaterno;
	private String correo;
	private String username;
	private String contrasena;
	@Column(name = "fecha_registro")
	private Date fechaRegistro;
	@Column(name = "estado_activo")
	private Boolean estadoActivo;

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

	public String getAppaterno() {
		return appaterno;
	}

	public void setAppaterno(String appaterno) {
		this.appaterno = appaterno;
	}

	public String getApmaterno() {
		return apmaterno;
	}

	public void setApmaterno(String apmaterno) {
		this.apmaterno = apmaterno;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Boolean getEstadoActivo() {
		return estadoActivo;
	}

	public void setEstadoActivo(Boolean estadoActivo) {
		this.estadoActivo = estadoActivo;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", appaterno=" + appaterno + ", apmaterno=" + apmaterno
				+ ", correo=" + correo + ", username=" + username + ", contrasena=" + contrasena + ", fechaRegistro="
				+ fechaRegistro + ", estadoActivo=" + estadoActivo + "]";
	}

	public Usuario() {
		super();
	}

}
