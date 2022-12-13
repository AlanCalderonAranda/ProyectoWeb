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

import com.uacm.proyecto.ecommerce.models.entity.Cliente;
import com.uacm.proyecto.ecommerce.models.entity.Entrega;
import com.uacm.proyecto.ecommerce.models.entity.Pago;
import com.uacm.proyecto.ecommerce.models.entity.Pedido;
import com.uacm.proyecto.ecommerce.models.entity.PedidoProducto;
import com.uacm.proyecto.ecommerce.models.service.IClienteService;
import com.uacm.proyecto.ecommerce.models.service.IEntregaService;
import com.uacm.proyecto.ecommerce.models.service.IPagoService;
import com.uacm.proyecto.ecommerce.models.service.IPedidoProductoService;
import com.uacm.proyecto.ecommerce.models.service.IPedidoService;
import com.uacm.proyecto.ecommerce.util.paginacion.PaginacionCalculos;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	@Autowired
	IClienteService clienteService;
	@Autowired
	IPedidoService pedidoService;
	@Autowired
	IPedidoProductoService pedidoProductoService;
	@Autowired
	IPagoService pagoService;
	@Autowired
	IEntregaService entregaService;
	
	@GetMapping("/listar")
	public String listar(Model model) {
		return "redirect:/cliente/listar/pag";
	}

	/* PAGINACION */
	@GetMapping("/listar/pag")
	public String listarPaginacion(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Cliente> clientes = clienteService.obtenerTodosPagina(pageRequest);
		PaginacionCalculos<Cliente> paginacionCalculos = new PaginacionCalculos<>("/cliente/listar/pag", clientes);
		model.addAttribute("titulo", "Clientes");
		model.addAttribute("listaClientes", clientes);
		model.addAttribute("pagina", paginacionCalculos);
		model.addAttribute("paginacion", true);
		return "/cliente/clientes";
	}

	@GetMapping("/crear")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("titulo", "Registrar Cliente");
		model.put("clienteNuevo", cliente);
		return "/cliente/registrarCliente";
		
	}

	@PostMapping("/crear")
	public String guardar(Cliente cliente, Map<String, Object> model) {
		List<Cliente> clientes = clienteService.obtenerTodos();
		for (int i = 0; i < clientes.size(); i++) {
			if (clientes.get(i).getCorreo().equals(cliente.getCorreo())) {
				System.out.println("NO PUEDES REGISTRAR 2 CORREOS IDENTICOS");
				model.put("titulo", "Registrar Cliente");
				model.put("clienteNuevo", cliente);
				return "/cliente/registrarCliente";
			}
			if (clientes.get(i).getUsername().equals(cliente.getUsername())) {
				System.out.println("NO PUEDES REGISTRAR 2 USERNAME IGUALES");
				model.put("titulo", "Registrar Cliente");
				model.put("clienteNuevo", cliente);
				return "/cliente/registrarCliente";
			}
		}
		clienteService.guardar(cliente);
		return "redirect:/cliente/listar/pag";
	}

	@GetMapping("/eliminar/{clienteId}")
	public String eliminar(@PathVariable(value = "clienteId") Long clienteId) {
		List<Pedido>  pedido = pedidoService.obtenerTodos();
		List<PedidoProducto> pps = pedidoProductoService.obtenerTodos();
		List<Pago> pago = pagoService.obtenerTodos();
		List<Entrega> entrega = entregaService.obtenerTodas();
		for(int i=0;i<pps.size();i++)
			if(pps.get(i).getPedido().getCliente()==clienteService.buscar(clienteId))
				pedidoProductoService.eliminar(pps.get(i).getId());
		
		
		for(int i=0;i<pedido.size();i++) 
			if(pedido.get(i).getCliente()==clienteService.buscar(clienteId)) {
				for(int j=0;j<entrega.size();j++) {
					if(entrega.get(j).getPedido()==pedido.get(i)) {
						entregaService.eliminar(entrega.get(j).getId());
					}
				}
				pedidoService.eliminar(pedido.get(i).getId());
			}
		for(int i=0;i<pago.size();i++)
			if(pago.get(i).getCliente()==clienteService.buscar(clienteId))
				pagoService.eliminar(pago.get(i).getId());
		clienteService.eliminar(clienteId);
		return "redirect:/cliente/listar/pag";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		Cliente clienteActual = null;
		if (id > 0) {
			clienteActual = clienteService.buscar(id);
		} else {
			return "/cliente/listar/pag";
		}
		model.put("clienteNuevo", clienteActual);
		model.put("titulo", "Editar Cliente");

		return "/cliente/registrarCliente";
	}

	@GetMapping("/crearpedido/{id}")
	public String creaPedido(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		Cliente clienteActual = null;
		if (id > 0) {
			clienteActual = clienteService.buscar(id);
		} else {
			return "/cliente/listar/pag";
		}
		model.put("cliente", clienteActual);
		model.put("titulo", "Generar Pedido");
		return "/cliente/pedidoClienteForm";
	}

	@GetMapping("/pedidos/{clienteId}")
	public String listarClientePedidos(@PathVariable(value = "clienteId") Long clienteId, Model model) {
		model.addAttribute("titulo", "Cliente-Pedidos");
		model.addAttribute("cliente", clienteService.buscar(clienteId));
		return "/cliente/cliente-pedidos";
	}

}