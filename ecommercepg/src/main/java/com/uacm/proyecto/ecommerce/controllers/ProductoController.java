package com.uacm.proyecto.ecommerce.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uacm.proyecto.ecommerce.models.entity.Producto;
import com.uacm.proyecto.ecommerce.models.entity.Vendedor;
import com.uacm.proyecto.ecommerce.models.service.IProductoService;
import com.uacm.proyecto.ecommerce.models.service.IVendedorService;
import com.uacm.proyecto.ecommerce.util.paginacion.PaginacionCalculos;

@Controller
@RequestMapping("/producto")
public class ProductoController {
	@Autowired
	IProductoService productoService;
	@Autowired
	IVendedorService vendedorService;

	@GetMapping("listar")
	public String listar(Model model) {
		return "redirect:/producto/listar/pag";
	}

	// Paginacion
	@GetMapping("/listar/pag")
	public String listarPaginacion(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 3);
		Page<Producto> productos = productoService.obtenerTodosPagina(pageRequest);
		PaginacionCalculos<Producto> paginacionCalculos = new PaginacionCalculos<>("/producto/listar/pag", productos);
		model.addAttribute("titulo", "Productos");
		model.addAttribute("listaProductos", productos);
		model.addAttribute("pagina", paginacionCalculos);
		model.addAttribute("paginacion", true);
		return "/producto/productos";
	}

	@GetMapping("/crear/{idVendedor}")
	public String crear(@PathVariable(value = "idVendedor") Long id, Map<String, Object> model, Producto producto) {
		Vendedor vendedor = vendedorService.buscar(id);
		model.put("vendedor", vendedor);
		model.put("titulo", "Registrar Producto");
		model.put("productoNuevo", producto);
		return "/producto/registrarProducto";
	}

	@PostMapping("/crear/{idVendedor}")
	public String guardar(@PathVariable(value = "idVendedor") Long vendedorId, Producto producto) {
		Vendedor vendedor = vendedorService.buscar(vendedorId);
		producto.setVendedor(vendedor);
		if(producto.getBonificacion()==null) {
			producto.setBonificacion(0.0);
		}
		if(producto.getDescuento()==null) {
			producto.setDescuento(0.0);
		}
		productoService.guardar(producto);
		return "redirect:/vendedor/productos/" + vendedorId;
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		// Llamar al DAO para editar
		Producto producto = null;
		Vendedor vendedor = null;
		if (id > 0) {
			producto = productoService.buscar(id);
			vendedor = producto.getVendedor();// Ocupamos el vendedor para pasarselo a nuesta vista
		} else {
			return "redirect:/vendedor/listar";
		}
		model.put("vendedor", vendedor);
		model.put("titulo", "EDITAR Producto");
		model.put("productoNuevo", producto);
		return "/producto/registrarProducto";
	}

	@GetMapping("/eliminar/{idProducto}")
	public String eliminar(@PathVariable(value = "idProducto") Long idProducto) {
		List<Producto> p = productoService.obtenerTodos();
		Vendedor v = new Vendedor();
		for(int i=0;i<p.size();i++) {
			if(p.get(i).getId() == idProducto) {
				v=p.get(i).getVendedor();
			}
		}
		productoService.eliminar(idProducto);
		return "redirect:/vendedor/productos/"+v.getId();
	}

}
