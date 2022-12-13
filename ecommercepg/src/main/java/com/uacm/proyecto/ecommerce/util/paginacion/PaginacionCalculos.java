package com.uacm.proyecto.ecommerce.util.paginacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PaginacionCalculos<T> {
	
	private String url;
	private Page<T> pagina;
	private List<Pagina> paginas;
	
	private int totalPaginas;
	private int numElementosPorPagina;
	private int paginaActual;
	public PaginacionCalculos(String url, Page<T> pagina) {
		this.url = url;
		this.pagina = pagina;
		//calcular en que pagina estamos inicializacion de la lista, etc
		paginas = new ArrayList<Pagina>();
		
		numElementosPorPagina = pagina.getSize();
		totalPaginas = pagina.getTotalPages();
		paginaActual = pagina.getNumber() + 1; //desde cero hasta -1

		int desde, hasta;
		
		/*si son muchas paginas 100 registros y las paginas deben mostrar 10 
		*entonces tendremos 10 paginas
		*/
		
		if(totalPaginas<=numElementosPorPagina) {
			desde=1;
			hasta=totalPaginas;
		}else {
			/**si tenemos 500 registros y la pagina debe mostrar 10 registros las paginas 
			 * serian 50 SON MUCHAS
			 */
			//existen casos por la pagina actual rango inicial
			if(paginaActual <= numElementosPorPagina/2) {
				desde =1 ;
				hasta = numElementosPorPagina;
			}//rango final
			else if(paginaActual >= totalPaginas - numElementosPorPagina/2) {
				desde = totalPaginas - numElementosPorPagina+1;
				hasta = numElementosPorPagina;
			}else {
				//rango intermedio
				desde = paginaActual - numElementosPorPagina/2;
				hasta = numElementosPorPagina;
			}
			
		}
		
		for(int i=0; i<hasta; i++) {
			paginas.add(new Pagina(desde+i,paginaActual==desde+i));
		}
		
		
	}//fin constructor
	
	public String getUrl() {
		return url;
	}
	public List<Pagina> getPaginas() {
		return paginas;
	}
	public int getTotalPaginas() {
		return totalPaginas;
	}
	public int getPaginaActual() {
		return paginaActual;
	}
	/*metodos de clase Page*/
	public boolean isFirst() {
		return pagina.isFirst();
	}
	
	public boolean isLast() {
		return pagina.isLast();
	}
	
	public boolean isHasNext() {
		return pagina.hasNext();
	}
	
	public boolean isHasPrevious() {
		return pagina.hasPrevious();
	}
	
	
}
