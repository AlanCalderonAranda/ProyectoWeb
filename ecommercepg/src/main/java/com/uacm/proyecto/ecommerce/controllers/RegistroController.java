package com.uacm.proyecto.ecommerce.controllers;


import java.nio.channels.ClosedByInterruptException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uacm.proyecto.ecommerce.models.entity.Cliente;
import com.uacm.proyecto.ecommerce.models.entity.Vendedor;
import com.uacm.proyecto.ecommerce.models.service.IClienteService;
import com.uacm.proyecto.ecommerce.models.service.IVendedorService;

@Controller
@RequestMapping("/")
public class RegistroController {

	@Autowired
	IVendedorService vendedorService;
	
	@Autowired
	IClienteService clienteService;
	
	@GetMapping("/")
	public String entrar(Model model){
		Cliente cliente = new Cliente();
		model.addAttribute("usuario",cliente);
		model.addAttribute("titulo", "Acceso al Sistema");
		return "/login/login";
	}
	
	@GetMapping("/login/registrar/vendedor")
	public String registroVendedor(Model model){
		Vendedor vendedor = new Vendedor();
		model.addAttribute("usuario",vendedor);
		model.addAttribute("titulo", "Acceso al Sistema");
		model.addAttribute("tipoRegistro","Vendedor");
		return "/login/registro";
	}
	
	@GetMapping("/login/registrar/cliente")
	public String registroCliente(Model model){
		Cliente cliente = new Cliente();
		model.addAttribute("usuario",cliente);
		model.addAttribute("titulo", "Acceso al Sistema");
		model.addAttribute("tipoRegistro","Cliente");
		return "/login/registro";
	}
	
	@PostMapping("/login/registrar")
	public String registrarVendedor(Model model,@RequestParam(name="tipoRegistro") String tipoRegistro,Vendedor vendedor){
		System.out.println("Se esta registrando un: "+tipoRegistro);
		if(tipoRegistro.equals("Vendedor")) {
			List<Vendedor> vendedores = vendedorService.obtenerTodos();
			for(int i=0;i<vendedores.size();i++) {
				if(vendedores.get(i).getCorreo().equals(vendedor.getCorreo()) 
						|| vendedores.get(i).getUsername().equals(generaUsername(vendedor.getNombre(),vendedor.getAppaterno(),vendedor.getApmaterno()))) {
					model.addAttribute("usuario",vendedor);
					model.addAttribute("titulo", "Acceso al Sistema");
					model.addAttribute("tipoRegistro","Vendedor");
					return "/login/registro";
				}
			}
			vendedor.prePersist();
			vendedor.setEstadoActivo(true);
			vendedor.setUsername(generaUsername(vendedor.getNombre(),vendedor.getAppaterno(),vendedor.getApmaterno()));
			vendedorService.guardar(vendedor);
		}
		if(tipoRegistro.equals("Cliente")) {
			Cliente cliente = new Cliente();
			cliente.setApmaterno(vendedor.getApmaterno());
			cliente.setAppaterno(vendedor.getAppaterno());
			cliente.setNombre(vendedor.getNombre());
			cliente.setCorreo(vendedor.getCorreo());
			cliente.setContrasena(vendedor.getContrasena());
			cliente.prePersist();
			cliente.setEstadoActivo(true);
			cliente.setMonedero(0.0);
			cliente.setUsername(generaUsername(cliente.getNombre(),cliente.getAppaterno(),cliente.getApmaterno()));
			clienteService.guardar(cliente);
		}
		return "redirect:/";
	}
	
	@PostMapping("/login/entrar")
	public String LogInVendedor(Cliente usuario,Model model){
		int bandera=0;
		Cliente cliente = new Cliente();
		Vendedor vendedor = new Vendedor();
		//Si es un cliente
		List<Cliente> listaClientes = clienteService.obtenerTodos();
		for(int i=0;i<listaClientes.size();i++) {
			//Si el usuario y contrasena de la Lista de Clientes 
			if((listaClientes.get(i).getCorreo().equals(usuario.getCorreo())) && (listaClientes.get(i).getContrasena().equals(usuario.getContrasena())) ) {
				cliente = listaClientes.get(i);
				bandera=1;
			}
		}
		//Si es un vendedor
		List<Vendedor> listaVendedores = vendedorService.obtenerTodos();
		for(int i=0;i<listaVendedores.size();i++) {
			//Si el usuario y contrasena de la Lista de Clientes 
			if((listaVendedores.get(i).getCorreo().equals(usuario.getCorreo())) && (listaVendedores.get(i).getContrasena().equals(usuario.getContrasena())) ) {
				vendedor = listaVendedores.get(i);
				bandera=2;
			}
		}
		if(bandera==1) {
			return "redirect:/cliente/pedidos/"+cliente.getId();
		}
		if(bandera==2) {
			return "redirect:/vendedor/productos/"+vendedor.getId();
		}
		if(bandera==1) {
			System.out.println("NO HAY USUARIO NI CLIENTE CON LOS DATOS PROPORCIONADOS");
			model.addAttribute("usuario",usuario);
			model.addAttribute("titulo", "Acceso al Sistema");
			return "/login/login";
		}
		return null;
	}
	
	public String generaUsername(String nombre,String apellidoP, String apellidoM) {
		//Si tienen espacios ya sea en la posicion 0 a 3 entonces se los borramos
		if(apellidoP.contains(" "))
			apellidoP.replace(" ","");
		
		if(nombre.contains(" "))
			nombre.replace(" ","");
		
		if(apellidoM.contains(" "))
			apellidoM.replace(" ","");
		String username=( (nombre.substring(0, 3)).concat( (apellidoP.substring(0, 3)).concat(apellidoM.substring(0, 3)) ) );
		return username.toLowerCase();
	}
	
}
