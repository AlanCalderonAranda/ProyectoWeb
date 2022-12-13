package com.uacm.proyecto.ecommerce.controllers;

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
import com.uacm.proyecto.ecommerce.models.service.IEntregaService;
import com.uacm.proyecto.ecommerce.util.paginacion.PaginacionCalculos;

@Controller
@RequestMapping("/entrega")
public class EntregaController {

	@Autowired
	IEntregaService entregaService;

	@GetMapping("/listar")
	public String listar(Model model) {

		model.addAttribute("listaEntregas", entregaService.obtenerTodas());
		model.addAttribute("paginacion", false);
		return "/entrega/entregas";
	}

	/*
	 * PAGINACIÓN
	 */

	@GetMapping("/listar/pag")
	public String listarPaginacion(@RequestParam(name = "page", defaultValue = "0") int pag, Model model) {

		Pageable pageRequest = PageRequest.of(pag, 3);
		Page<Entrega> entregas = entregaService.obtenerTodosPagina(pageRequest);

		PaginacionCalculos<Entrega> paginacionCalculos = new PaginacionCalculos<>("/entrega/listar/pag", entregas);

		model.addAttribute("titulo", "Entregas");
		model.addAttribute("listaPedidos", entregas);
		model.addAttribute("pagina", paginacionCalculos);

		model.addAttribute("paginacion", true);

		return "entrega/entregas";
	}

	@GetMapping("/crear")
	public String crear(Map<String, Object> model) {
		Entrega entrega = new Entrega();

		model.put("titulo", "Registrar Entrega");
		model.put("entregaNueva", entrega);
		return "/entrega/registrarEntrega";
	}

	@PostMapping("/crear")
	public String guardar(Entrega entrega) {
		entregaService.guardar(entrega);
		return "redirect:/entrega/listar/pag";
	}

	// No estoy seguro sí así se haría la actualización de un producto...
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		// Llamar al DAO para editar
		Entrega entrega = null;
		if (id > 0) {
			entrega = entregaService.buscar(id);
		} else {
			return "redirect:/entregas/listar";
		}

		entregaService.guardar(entrega);
		model.put("titulo", "Editar Entrega");

		return "registrarEntrega";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id) {
		// Llamar al DAO para eliminar
		if (id != null && id > 0) {
			entregaService.eliminar(id);
		}

		return "redirect:/entrega/listar";
	}

}
