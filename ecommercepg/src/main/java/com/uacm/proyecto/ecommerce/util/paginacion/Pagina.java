package com.uacm.proyecto.ecommerce.util.paginacion;

public class Pagina {
	private int numero;
	private boolean actual;
	
	public Pagina(int numero, boolean actual) {
		this.numero = numero;
		this.actual = actual;
	}

	public int getNumero() {
		return numero;
	}
	
	public boolean isActual() {
		return actual;
	}
	
	
}
