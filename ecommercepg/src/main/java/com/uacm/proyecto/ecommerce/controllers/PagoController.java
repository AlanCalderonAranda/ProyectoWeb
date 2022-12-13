package com.uacm.proyecto.ecommerce.controllers;

import java.util.ArrayList;
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

import com.uacm.proyecto.ecommerce.models.entity.Pago;
import com.uacm.proyecto.ecommerce.models.service.ClienteServiceImpl;
import com.uacm.proyecto.ecommerce.models.service.IClienteService;
import com.uacm.proyecto.ecommerce.models.service.IPagoService;
import com.uacm.proyecto.ecommerce.util.paginacion.PaginacionCalculos;

@Controller
@RequestMapping("/pago")
public class PagoController {
	@Autowired
	IPagoService pagoService;
	@Autowired
	IClienteService clienteService;

	@GetMapping("/listar/{clienteId}")
	public String listar(Model model, @PathVariable(name = "clienteId") Long clienteId) {
		List<Pago> pagos = pagoService.obtenerTodos();
		List<Pago> pagosCliente = new ArrayList<>();
		for (int i = 0; i < pagos.size(); i++) {
			if (pagos.get(i).getCliente().getId() == clienteId) {
				pagosCliente.add(pagos.get(i));
			}
		}
		model.addAttribute("clienteId", clienteId);
		model.addAttribute("listaPagos", pagosCliente);
		return "/pago/pagos";
	}

	@GetMapping("/listar/pag")
	public String listarPaginacion(@RequestParam(name = "page", defaultValue = "0") int pag, Model model) {
		Pageable pageRequest = PageRequest.of(pag, 3);
		Page<Pago> pagos = pagoService.obtenerTodosPagina(pageRequest);
		PaginacionCalculos<Pago> paginacionCalculos = new PaginacionCalculos<>("/pedido/listar/pag", pagos);
		model.addAttribute("titulo", "Pagos");
		model.addAttribute("listaPagos", pagos);
		model.addAttribute("pagina", paginacionCalculos);
		model.addAttribute("paginacion", true);
		return "pedido/pedidos";
	}

	@GetMapping("/crear/{clienteId}")
	public String crear(Map<String, Object> model, @PathVariable(name = "clienteId") Long clienteId) {
		Pago pago = new Pago();
		model.put("clienteId", clienteId);
		model.put("titulo", "Registrar Pago");
		model.put("pagoNuevo", pago);
		return "/pago/registrarPago";
	}

	@PostMapping("/crear/{clienteId}")
	public String guardar(Pago pago, @PathVariable(name = "clienteId") Long clienteId, Map<String, Object> model) {
		List<Pago> listaPagos = pagoService.obtenerTodos();
		for (int i = 0; i < listaPagos.size(); i++) {
			// Comprobamos que no tenga la misma tarjeta ya registrada con anterioridad
			if (listaPagos.get(i).getNumTarjeta().equals(pago.getNumTarjeta())) {
				pago.setNumTarjeta(null);
				model.put("clienteId", clienteId);
				model.put("titulo", "Registrar Pago");
				model.put("pagoNuevo", pago);
				return "/pago/registrarPago";
			} else {
				// Si el CCV es menor a 3 o mayor a 4 entonces no lo dejamos procesar
				System.out.println("El tamano del CCV es: " + pago.getCCV().length());
				if (pago.getCCV().length() < 3 || pago.getCCV().length() > 4) {
					pago.setCCV(null);
					model.put("clienteId", clienteId);
					model.put("titulo", "Registrar Pago");
					model.put("pagoNuevo", pago);
					return "/pago/registrarPago";
				} else {
					pago.setCliente(clienteService.buscar(clienteId));
					pagoService.guardar(pago);
					return "redirect:/pago/listar/"+clienteId;
				}
			}
		}
		return "redirect:/pago/listar/"+clienteId;
	}

	@GetMapping("/editar/{pagoId}")
	public String editar(@PathVariable(value = "pagoId") Long pagoId, Map<String, Object> model) {
		// Llamar al DAO para editar
		Pago pago = null;
		if (pagoId > 0) {
			pago = pagoService.buscar(pagoId);
		} else {
			return "redirect:/pedidos/listar";
		}
		pagoService.guardar(pago);
		model.put("clienteId", pago.getCliente().getId());
		model.put("titulo", "Editar Pago");
		model.put("pagoNuevo", pago);
		return "/pago/registrarPago";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id) {
		// Llamar al DAO para eliminar
		if (id != null && id > 0) {
			pagoService.eliminar(id);
		}
		return "redirect:/pago/listar";
	}

}