package com.uacm.proyecto.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uacm.proyecto.ecommerce.models.service.IRolService;

@Controller
@RequestMapping("/rol")
public class RolController 
{
	
	@Autowired
	IRolService rolService;
	
	@GetMapping("listar")
	public String listar(Model model)
	{
		
		model.addAttribute("listaRoles", rolService.obtenerTodos());
		return "/rol/roles";
	}
	
}
