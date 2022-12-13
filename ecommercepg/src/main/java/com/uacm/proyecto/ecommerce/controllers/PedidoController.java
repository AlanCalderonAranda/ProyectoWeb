package com.uacm.proyecto.ecommerce.controllers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.uacm.proyecto.ecommerce.models.entity.Producto;
import com.uacm.proyecto.ecommerce.models.entity.Vendedor;
import com.uacm.proyecto.ecommerce.models.service.IClienteService;
import com.uacm.proyecto.ecommerce.models.service.IEntregaService;
import com.uacm.proyecto.ecommerce.models.service.IPagoService;
import com.uacm.proyecto.ecommerce.models.service.IPedidoProductoService;
import com.uacm.proyecto.ecommerce.models.service.IPedidoService;
import com.uacm.proyecto.ecommerce.models.service.IProductoService;
import com.uacm.proyecto.ecommerce.models.service.IVendedorService;
import com.uacm.proyecto.ecommerce.util.paginacion.PaginacionCalculos;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
	@Autowired
	IPedidoService pedidoService;
	@Autowired
	IProductoService productoService;
	@Autowired
	IClienteService clienteService;
	@Autowired
	IPedidoProductoService pedidoProductoService;
	@Autowired
	IEntregaService entregaService;
	@Autowired
	IPagoService pagoService;
	@Autowired
	IVendedorService vendedorService;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDateTime horaActual = LocalDateTime.now();

	@GetMapping("/listar")//Si pone listar lo mandamos a la paginacion
	public String listar(Model model) {
		return "redirect:/pedido/listar/pag";
	}

	@GetMapping("/listar/pag")
	public String listarPaginacion(@RequestParam(name = "page", defaultValue = "0") int pag, Model model) {
		Pageable pageRequest = PageRequest.of(pag, 3);
		Page<Pedido> pedidos = pedidoService.obtenerTodosPagina(pageRequest);
		PaginacionCalculos<Pedido> paginacionCalculos = new PaginacionCalculos<>("/pedido/listar/pag", pedidos);
		model.addAttribute("titulo", "Pedidos");
		model.addAttribute("listaPedidos", pedidos);
		model.addAttribute("pagina", paginacionCalculos);
		model.addAttribute("paginacion", true);
		return "pedido/pedidos";
	}

	// ESTO ES PARA MANDAR A GENERAR PEDIDO
	@GetMapping("/guardar/{clienteId}")
	public String crearPedido(@PathVariable("clienteId") Long clienteId, Map<String, Object> model) {
		List<Producto> todosProducto = productoService.obtenerTodos();
		if(todosProducto.size()<=0) {//Si no hay productos no podemos generar un pedido
			return "redirect:/cliente/listar/pag";
		}else {
			Pedido pedido = new Pedido();
			model.put("titulo", "Nuevo Pedido");
			model.put("listaProductos", productoService.obtenerTodos());
			model.put("clienteId", clienteId);
			model.put("horaActual", horaActual);
			model.put("pedidoNuevo", pedido);
			return "pedido/pruebaPedidos";
		}
	}

	@PostMapping("/guardar/{clienteId}")
	public String crear(Pedido pedido, @RequestParam(name = "id") List<Long> listaIDProductos,@RequestParam(name = "numpiezas") List<Integer> numpiezas,
			@RequestParam(name = "infoAdicional") String infopedido,Map<String, Object> model,
			@RequestParam(name = "notasAdicionales") String notasAdicionales,
			@PathVariable(name = "clienteId") Long clienteId,
			@RequestParam(name = "fechaDeEntrega", defaultValue = "#{new java.util.Date()}") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date fechaDeEntrega,
			@RequestParam(name = "horaDeEntrega") LocalTime horaDeEntrega, Entrega entrega) {
		if (listaIDProductos == null || listaIDProductos.isEmpty() || listaIDProductos.size() <= 0) {
			return "pedido/pruebaPedidos";
		}
		Double Total = 0.0;
		Cliente cliente = clienteService.buscar(clienteId);

		for (int i = 0; i < listaIDProductos.size(); i++) {
			Producto p = productoService.buscar(listaIDProductos.get(i));
			if (numpiezas.get(i) > p.getNumPiezas()) {// Si se seleccionan mas piezas de las que hay disponibles
														// entonces refrescamos la pagina
				model.put("titulo", "Nuevo Pedido");
				model.put("listaProductos", productoService.obtenerTodos());
				model.put("clienteId", clienteId);
				model.put("horaActual", horaActual);
				model.put("pedidoNuevo", pedido);
				return "pedido/pruebaPedidos";
			} else {
				if (p.getDescuento() <= 0) {
					Total = Total + (p.getPrecio() * numpiezas.get(i));// Sumamos el precio de cada uno de los productos
				}else {
					Total+=((p.getPrecio()-(p.getPrecio()*p.getDescuento())/100) * numpiezas.get(i));
				}
				// Por cada Producto le damos el % de bonificacion en el monedero al cliente
				if(p.getBonificacion()!=0) {
					Double bono = ((p.getPrecio()-(p.getPrecio()*p.getDescuento())/100));
					bono = (bono*p.getBonificacion()/100);
					cliente.setMonedero(cliente.getMonedero()+(bono* numpiezas.get(i)));
				}
				p.setNumPiezas(p.getNumPiezas()-numpiezas.get(i));//Como compramos X piezas entonces se las reducimos
			}
		}
		// Como luego me actualiza debido a que si tiene un pedido otro cliente o en si
		// el mismo con los mismos detalles me lo actualiza
		// Tengo que rerorrer todos los pedidos e ir recorriendo el que ya tengo para
		// que no haya repetidos y asignandole un nuevo ID
		List<Pedido> pedidos = pedidoService.obtenerTodos();
		for (int i = 0; i < pedidos.size(); i++) {
			for (int j = 0; j < pedidos.size(); j++) {
				if (pedidos.get(i).getId() == pedido.getId()) {
					pedido.setId(pedido.getId() + 1);
				}
			}
		}
		pedido.setCliente(cliente);
		pedido.setTotalPagar(Total);
		pedido.setInfoAdicional(infopedido);
		pedido.prePersist();// Le pasamos que la fecha sea la de la creacion del pedido
		pedido.setEstado(0);
		pedidoService.guardar(pedido); // Aqui ya lo guarde en la Base de Datos
		// Ahora para la Entrega
		List<Entrega> entregas = entregaService.obtenerTodas();
		for (int i = 0; i < entregas.size(); i++) {
			for (int j = 0; j < entregas.size(); j++) {
				if (entregas.get(i).getId() == entrega.getId()) {
					entrega.setId(entrega.getId() + 1);
				}
			}
		}
		entrega.setFechaEntrega(fechaDeEntrega);
		entrega.setEstado(false);
		entrega.setIntentos(0);// Como es un pedido nuevo no tiene Intentos de Entrega
		entrega.setPedido(pedido);
		entrega.setHoraEntrega(horaDeEntrega);
		entregaService.guardar(entrega);
		// Para PedidoProducto

		for (int i = 0; i < listaIDProductos.size(); i++) {
			PedidoProducto pedidoProducto = new PedidoProducto();
			Producto p = productoService.buscar(listaIDProductos.get(i));
			pedidoProducto.setProducto(p);

			pedidoProducto.setInfoAdicional(
					(infopedido.concat(", " + entrega.getPuntoEntrega())).concat(", " + notasAdicionales));
			pedidoProducto.setSubtotalPagar(Total);
			pedidoProducto.setPedido(pedido); // Le asociamos el ultimo pedido que generamos
			pedidoProductoService.guardar(pedidoProducto);
		}
		return "redirect:/cliente/pedidos/"+clienteId;
	}
	// AQUI TERMINA

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		// Llamar al DAO para editar
		Pedido pedido = null;
		Cliente cliente = null;
		if (id > 0) {
			pedido = pedidoService.buscar(id);
			cliente = pedido.getCliente();
		} else {
			return "redirect:/pedido/listar";
		}
		// pedidoService.guardar(pedido);
		model.put("cliente", cliente);
		model.put("titulo", "EDITAR Pedido");
		model.put("pedidoNuevo", pedido);
		return "pedido/registrarPedido";
	}

	@GetMapping("/eliminar/{idPedido}")
	public String eliminar(@PathVariable(value = "idPedido") Long idPedido) {
		// Si yo elimino un Pedido se tiene que eliminar el PedidoProducto, La entrega
		// Primero eliminamos el PedidoProducto
		Cliente cliente = pedidoService.buscar(idPedido).getCliente();
		List<PedidoProducto> ppds = pedidoProductoService.obtenerTodos();
		List<Producto> p = new ArrayList<>();

		// Recorro todos los PedidosProductos en la BD
		for (int i = 0; i < ppds.size(); i++) {
			if (ppds.get(i).getPedido() == pedidoService.buscar(idPedido)) {// Los pedidos que coincidan los borro
				pedidoProductoService.eliminar(ppds.get(i).getId());
			}
		}
		// Ahora las entregas
		List<Entrega> entregas = entregaService.obtenerTodas();
		// Ahora tenemos que buscar la entrega asociada a nuestro pedido y borrarla
		for (int i = 0; i < entregas.size(); i++) {
			if (entregas.get(i).getPedido() == pedidoService.buscar(idPedido)) {
				entregaService.eliminar(entregas.get(i).getId());
			}
		}
		// Ahora eliminamos el pedido
		pedidoService.eliminar(idPedido);
		return "redirect:/cliente/pedidos/" + cliente.getId();
	}

	@GetMapping("/pedidos/{vendedorId}")
	public String PedidosVendedor(@PathVariable(value = "vendedorId") Long vendedorId, Map<String, Object> model) {
		
		Vendedor vendedor = vendedorService.buscar(vendedorId);
		List<PedidoProducto> ppd = pedidoProductoService.obtenerTodos();
		System.out.println("Tenemos "+ppd.size()+" pedidos Productos en lista");
		List<Producto> p = new ArrayList<>();// Hago una lista de los productos que contengan los vendedores
		List<Pedido> pedidos = new ArrayList<>();
		
		for (int i = 0; i < ppd.size(); i++) {
			System.out.println("VendedorID de ppd es: "+ppd.get(i).getProducto().getNombre());
			System.out.println("VendedorID del vendedor es: "+vendedorId);
			if (ppd.get(i).getProducto().getVendedor() == vendedor) {
				pedidos.add(ppd.get(i).getPedido());
				// pedidos.get(i).getId();
			}
		}
		if (pedidos.size() <= 0) {
			return "redirect:/vendedor/productos/"+vendedorId;
		}
		//Par eliminar los pedidos repetidos de un vendedor
		pedidos = pedidos.stream().distinct().collect(Collectors.toList());
		model.put("titulo", "Pedidos de un Vendedor");
		model.put("vendedor", vendedor);
		model.put("pedidos", pedidos);
		return "/vendedor/vendedor-pedidos";
	}

	@GetMapping("/pagar/{pedidoId}")
	public String pagarPedido(@PathVariable(value = "pedidoId") Long idPedido, Map<String, Object> model) {
		List<PedidoProducto> pdL = pedidoProductoService.obtenerTodos();
		int bandera = 0;
		Pedido pedido = pedidoService.buscar(idPedido);
		Cliente cliente = pedido.getCliente();
		if (pedido.getEstado() == 1) {
			System.out.println("NO SE PUEDE PAGAR UN PEDIDO YA PAGADO");
			return "redirect:/cliente/pedidos/" + cliente.getId();
		}
		List<Pago> pagos = pagoService.obtenerTodos();
		for (int i = 0; i < pagos.size(); i++) {
			// Necesitamos comprobar que el cliente tenga un pago para poder realizar el
			// pago de lo contrario lo mandamos a registrar uno
			if (pagos.get(i).getCliente() == cliente) {
				bandera = 1;
			}
		}
		if (bandera < 1) {// Si no tiene pagos entonces lo mando a crearlo
			return "redirect:/pago/crear/" + cliente.getId();
		} else {// Si si tiene entonces si puede realizar el pedido
			if (pedido == null) {// Si el pedido es nulo
				return "redirect:/cliente/listar";
			} else {

				// System.out.println("El pedido se encontro");
				if (pedidoProductoService.obtenerTodos().size() <= 0) {
					System.out.println("NO HAY PEDIDOS PRODUCTOS");
					return "redirect:/cliente/listar";
				} else {
					// System.out.println("SIII HAY PEDIDOS PRODUCTOS");
					
					//Compruebo que mi pedido tenga la asociacion con un Pedido Producto
					for (int i = 0; i < pdL.size(); i++) {
						if (pdL.get(i).getPedido().getId() == idPedido) {// Compruebo que el pedidoId sea el mismo que
																			// eh seleccionado
							model.put("cliente", pedido.getCliente().getId());
							model.put("titulo", "Pagar Pedido");
							model.put("pedido", pedidoService.buscar(idPedido));
							Pago pago = new Pago();
							model.put("pagoNuevo", pago);
							return "pedido/pagarPedido";
						} else {
							System.out.println("No HAY PEDIDOS PRODUCTO");
						}
					}
					return "redirect:/cliente/pedidos/"+pedido.getCliente().getId();
				}
			}
		}
	}

	@PostMapping("/pagar/{pedidoId}")
	public String pagoPedido(@PathVariable(value = "pedidoId") Long idPedido, Model model,
			@RequestParam(name = "nombre") String nombre, @RequestParam(name = "clienteId") Long clienteId,
			@RequestParam(name = "CCV") String CCV, @RequestParam(name = "numTarjeta") String numTarjeta,
			@RequestParam(name = "fechaExpiracion", defaultValue = "#{new java.util.Date()}") @DateTimeFormat(pattern = "yyyy-MM") java.util.Date fechaExpiracion){
		Pedido pedido = pedidoService.buscar(idPedido);
		List<Entrega> entregas = entregaService.obtenerTodas();
		
		Cliente cliente = clienteService.buscar(clienteId);
		// Aqui tenemos la lista de Todos los pagos
		List<Pago> pago = pagoService.obtenerTodos();// Lista de todos los Pagos que tenemos en la BD
		for (int i = 0; i < pago.size(); i++) {// Recorremos todos los pagos en la BD
			if (pago.get(i).getCliente() == cliente) {
				if (pago.get(i).getTitular().equals(nombre)) {
					if (pago.get(i).getNumTarjeta().equals(numTarjeta)) {
						if (pago.get(i).getCCV().equals(CCV)) {
							pedido.setPago(pago.get(i));// Le paso el pago con el que lo hice
							pedido.setEstado(1);// Como ya esta pagado lo pasamos a un estado Activo
							//Ahora tenemos que activar la entrega
							for(int j=0;j<entregas.size();j++) {
								if(entregas.get(i).getPedido() == pedido) {
									Entrega entrega = entregas.get(i);
									entrega.setEstado(true);
									entregaService.guardar(entrega);
								}
							}
							pedidoService.guardar(pedido);
						} else {
							System.out.println("ERROR EN EL CCV");
						}
					} else {
						System.out.println("ERROR EN LA TARJETA");
					}
				} else {
					System.out.println("ERROR EN EL TITULAR");
				}
			}
		}
		return "redirect:/cliente/pedidos/" + pedido.getCliente().getId();
	}

	@GetMapping("/pedidosproducto/{pedidoId}")
	public String detallesPedido(@PathVariable(value = "pedidoId") Long idPedido, Map<String, Object> model) {
		List<PedidoProducto> pps = pedidoProductoService.obtenerTodos();
		List<Entrega> entregas = entregaService.obtenerTodas();
		Entrega entrega = null;
		List<Producto> listaProductos = new ArrayList<>();// Creamos una lista vacia de los posibles productos del pedido
		for (int i = 0; i < pps.size(); i++)
			if (pps.get(i).getPedido() == pedidoService.buscar(idPedido)) // Por cada PedidoProducto buscamos el pedido
																	 	  // que seleccionamos
				listaProductos.add(pps.get(i).getProducto());// Cada producto se guarda de cada PedidoProducto del
																// pedido seleccionado
		for (int i = 0; i < entregas.size(); i++)
			if (entregas.get(i).getPedido() == pedidoService.buscar(idPedido))
				entrega = entregas.get(i);

		if (entrega == null) {// Si no hay entregas entonces no podemos mostrar datos de la entrega
			return "redirect:/cliente/pedidos/" + pedidoService.buscar(idPedido).getCliente().getId();
		}
		model.put("listaProductos", listaProductos);// Le paso la lista de los productos asociados al pedido
		model.put("entrega", entrega);
		model.put("cliente", pedidoService.buscar(idPedido).getCliente());

		model.put("totalPedido", pedidoService.buscar(idPedido).getTotalPagar());
		return "/pedido/pedidosProducto";
	}
}