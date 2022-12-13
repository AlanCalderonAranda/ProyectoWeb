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

import com.uacm.proyecto.ecommerce.models.entity.Entrega;
import com.uacm.proyecto.ecommerce.models.entity.Pedido;
import com.uacm.proyecto.ecommerce.models.entity.PedidoProducto;
import com.uacm.proyecto.ecommerce.models.entity.Producto;
import com.uacm.proyecto.ecommerce.models.entity.Vendedor;
import com.uacm.proyecto.ecommerce.models.service.IEntregaService;
import com.uacm.proyecto.ecommerce.models.service.IPedidoProductoService;
import com.uacm.proyecto.ecommerce.models.service.IPedidoService;
import com.uacm.proyecto.ecommerce.models.service.IProductoService;
import com.uacm.proyecto.ecommerce.models.service.IVendedorService;
import com.uacm.proyecto.ecommerce.util.paginacion.PaginacionCalculos;

@Controller
@RequestMapping("/vendedor")
public class VendedorController {
	@Autowired
	IVendedorService vendedorService;
	@Autowired
	IProductoService productoService;
	@Autowired
	IPedidoProductoService pedidoProductoService;
	@Autowired
	IPedidoService pedidoService;
	@Autowired
	IEntregaService entregaService;

	@GetMapping("/listar")
	public String listar(Model model) {
		return "redirect:/vendedor/listar/pag";
	}

	
	@GetMapping("/listar/pag")
	public String listarPaginacion(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Vendedor> vendedores = vendedorService.obtenerTodosPagina(pageRequest);
		PaginacionCalculos<Vendedor> paginacionCalculos = new PaginacionCalculos<>("/vendedor/listar/pag", vendedores);
		model.addAttribute("titulo", "Vendedores Pag");
		model.addAttribute("listaVendedores", vendedores);
		model.addAttribute("pagina", paginacionCalculos);
		model.addAttribute("paginacion", true);

		return "/vendedor/vendedores";
	}

	@GetMapping("/productos/{vendedorId}")
	public String listarVendedorProductos(@PathVariable(value = "vendedorId") Long vendedorId, Model model) {
		model.addAttribute("titulo", "Vendedor-Productos");
		model.addAttribute("vendedor", vendedorService.buscar(vendedorId));
		return "/vendedor/vendedor-productos";
	}

	@GetMapping("/crear")
	public String crear(Map<String, Object> model) {
		Vendedor vendedor = new Vendedor();
		model.put("titulo", "Registrar Vendedor");
		model.put("vendedorNuevo", vendedor);
		return "/vendedor/registrarVendedor";
	}

	@PostMapping("/crear")
	public String guardar(Vendedor vendedor) {
		if (vendedor.getId() != null) {
			System.out.println("El Vendedor ya existe! (post) " + vendedor.toString() + "Actualizando...");
			vendedorService.guardar(vendedor);
		} else {
			System.out.println("El Vendedor no existe, Registrando...");
			vendedorService.guardar(vendedor);
			System.out.println("Registrado con Exito!");
		}
		return "redirect:/vendedor/listar/pag";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id) {
		Vendedor vendedorEliminar = vendedorService.buscar(id);
		List<PedidoProducto> pedidoProducto = pedidoProductoService.obtenerTodos();
		List<Entrega> entregas = entregaService.obtenerTodas();
		
		
		for(int i=0;i<pedidoProducto.size();i++) {
			for(int j=0;j<entregas.size();j++) {
				if(entregas.get(j).getPedido() == pedidoProducto.get(i).getPedido()) {
					entregaService.eliminar(entregas.get(i).getId());
				}
			}
			if(pedidoProducto.get(i).getProducto().getVendedor() == vendedorEliminar) {
				if(pedidoProducto.get(i).getPedido().getPago()!=null) {
					pedidoProducto.get(i).getPedido().setPago(null);//Le desvinculamos el pago
				}
				pedidoProductoService.eliminar(pedidoProducto.get(i).getId());
				productoService.eliminar(pedidoProducto.get(i).getProducto().getId());
				pedidoService.eliminar(pedidoProducto.get(i).getPedido().getId());
			}
		}
		vendedorService.eliminar(id);
		return "redirect:/vendedor/listar/pag";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		Vendedor vendedorActual = null;
		model.put("titulo", "Editar Vendedor");

		if (id > 0) {
			vendedorActual = vendedorService.buscar(id);
		} else {
			return "/vendedor/listar/pag";
		}
		model.put("vendedorNuevo", vendedorActual);

		return "/vendedor/registrarVendedor";
	}

}
