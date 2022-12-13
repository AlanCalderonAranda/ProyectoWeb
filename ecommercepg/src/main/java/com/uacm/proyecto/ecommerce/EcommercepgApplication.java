package com.uacm.proyecto.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.uacm.proyecto.ecommerce.models.entity.Cliente;
import com.uacm.proyecto.ecommerce.models.entity.Pago;
import com.uacm.proyecto.ecommerce.models.entity.Pedido;
import com.uacm.proyecto.ecommerce.models.entity.PedidoProducto;
import com.uacm.proyecto.ecommerce.models.entity.Producto;
import com.uacm.proyecto.ecommerce.models.entity.Vendedor;
import com.uacm.proyecto.ecommerce.models.service.IClienteService;
import com.uacm.proyecto.ecommerce.models.service.IPagoService;
import com.uacm.proyecto.ecommerce.models.service.IPedidoProductoService;
import com.uacm.proyecto.ecommerce.models.service.IPedidoService;
import com.uacm.proyecto.ecommerce.models.service.IProductoService;
import com.uacm.proyecto.ecommerce.models.service.IVendedorService;

@SpringBootApplication
public class EcommercepgApplication implements CommandLineRunner {
	@Autowired
	IProductoService productoService;
	
	@Autowired
	IVendedorService vendedorService;
	
	@Autowired
	IClienteService clienteService;
	
	@Autowired
	IPedidoService pedidoService;
	
	@Autowired
	IPedidoProductoService pedidoProductoService;
	
	@Autowired
	IPagoService pagoService;
	
	public static void main(String[] args) {
		SpringApplication.run(EcommercepgApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Vendedor v1 = new Vendedor();
		v1.setNombre("Joaquin");
		v1.setAppaterno("Lopez");
		v1.setApmaterno("Doriga");
		v1.setContrasena("abc123");
		v1.setCorreo("joaquin@example.com");
		v1.setUsername("joaquin");
		v1.setEstadoActivo(true);

		Vendedor v2 = new Vendedor();
		v2.setNombre("Viridiana");
		v2.setAppaterno("Sanchez");
		v2.setApmaterno("Mane");
		v2.setContrasena("abc123");
		v2.setCorreo("viry@example.com");
		v2.setUsername("viry23");
		v2.setEstadoActivo(true);

		Vendedor v3 = new Vendedor();
		v3.setNombre("Veronica");
		v3.setAppaterno("Arciaga");
		v3.setApmaterno("Medina");
		v3.setContrasena("abc123");
		v3.setCorreo("ver@example.com");
		v3.setUsername("vero10");
		v3.setEstadoActivo(true);

		Vendedor v4 = new Vendedor();
		v4.setNombre("Marco");
		v4.setAppaterno("Muñiz");
		v4.setApmaterno("Figueroa");
		v4.setContrasena("abc123");
		v4.setCorreo("marco@example.com");
		v4.setUsername("marc");
		v4.setEstadoActivo(true);

		Vendedor v5 = new Vendedor();
		v5.setNombre("Rocio");
		v5.setAppaterno("Estrada");
		v5.setApmaterno("Reyes");
		v5.setContrasena("abc123");
		v5.setCorreo("rocio@example.com");
		v5.setUsername("rocy");
		v5.setEstadoActivo(true);

		Vendedor v6 = new Vendedor();
		v6.setNombre("Mario");
		v6.setAppaterno("Moreno");
		v6.setApmaterno("Garcia");
		v6.setContrasena("abc123");
		v6.setCorreo("mario@example.com");
		v6.setUsername("mar");
		v6.setEstadoActivo(true);

		Vendedor v7 = new Vendedor();
		v7.setNombre("Rocio");
		v7.setAppaterno("Estrada");
		v7.setApmaterno("Reyes");
		v7.setContrasena("abc123");
		v7.setCorreo("rocio@example.com");
		v7.setUsername("rocy");
		v7.setEstadoActivo(true);

		Vendedor v8 = new Vendedor();
		v8.setNombre("Juan");
		v8.setAppaterno("Luna");
		v8.setApmaterno("Reyes");
		v8.setContrasena("abc123");
		v8.setCorreo("juan@example.com");
		v8.setUsername("juanito");
		v8.setEstadoActivo(true);

		Vendedor v9 = new Vendedor();
		v9.setNombre("Alberto");
		v9.setAppaterno("Sanchez");
		v9.setApmaterno("Lopez");
		v9.setContrasena("abc123");
		v9.setCorreo("albert@example.com");
		v9.setUsername("albert");
		v9.setEstadoActivo(true);

		Vendedor v10 = new Vendedor();
		v10.setNombre("Mauricio");
		v10.setAppaterno("Sales");
		v10.setApmaterno("Cardenas");
		v10.setContrasena("abc123");
		v10.setCorreo("mau@example.com");
		v10.setUsername("mauri");
		v10.setEstadoActivo(true);

		Vendedor v11 = new Vendedor();
		v11.setNombre("Antonio");
		v11.setAppaterno("de los Reyes");
		v11.setApmaterno("Guzman");
		v11.setContrasena("abc123");
		v11.setCorreo("anton@example.com");
		v11.setUsername("anton");
		v11.setEstadoActivo(true);

		Vendedor v12 = new Vendedor();
		v12.setNombre("Hugo");
		v12.setAppaterno("Sanchez");
		v12.setApmaterno("Sanchez");
		v12.setContrasena("abc123");
		v12.setCorreo("hugo@example.com");
		v12.setUsername("pentapichichi");
		v12.setEstadoActivo(true);

		vendedorService.guardar(v2);
		vendedorService.guardar(v3);
		vendedorService.guardar(v4);
		vendedorService.guardar(v5);
		vendedorService.guardar(v6);
		vendedorService.guardar(v7);
		vendedorService.guardar(v8);
		vendedorService.guardar(v9);
		vendedorService.guardar(v10);
		vendedorService.guardar(v11);
		vendedorService.guardar(v12);
		
		/*
		Producto p1 = new Producto();
		p1.setNombre("Refrigerador MABE gris");
		p1.setDescripcion("19 pies");
		p1.setDescuento("10 %");
		p1.setEstado(1); // activo
		p1.setInformacionAdicional("Nuevo");
		p1.setNumPiezas(7);
		p1.setPrecio(14567.87);
		//p1.setVendedor(v1);
		productoService.guardar(p1);
		
		
		Producto p2 = new Producto();
		p2.setNombre("Televisión LG negra 60 pulg");
		p2.setDescripcion("4K IA");
		p2.setDescuento("20 %");
		p2.setEstado(1); // activo
		p2.setInformacionAdicional("Television LG 4k 60¨");
		p2.setNumPiezas(7);
		p2.setPrecio(12334.50);
		p2.setVendedor(v2);
		productoService.guardar(p2);

		Producto p3 = new Producto();
		p3.setNombre("Televisión LG negra 42 pulg");
		p3.setDescripcion("4K IA");
		p3.setEstado(1); // activo
		p3.setInformacionAdicional("LG nueva");
		p3.setNumPiezas(7);
		p3.setPrecio(5800.50);
		p3.setVendedor(v2);
		productoService.guardar(p3);
		
		Producto p4 = new Producto();
		p4.setNombre("Laptop Dell");
		p4.setDescripcion("latitude 3390");
		p4.setEstado(1); // activo
		p4.setInformacionAdicional("Laptop Touch con funda de Regalo");
		p4.setNumPiezas(3);
		p4.setPrecio(3500.10);
		p4.setVendedor(v2);
		productoService.guardar(p4);
		
		Producto p5 = new Producto();
		p5.setNombre("Mac Book");
		p5.setDescripcion("Pro 4gen");
		p5.setEstado(1); // activo
		p5.setInformacionAdicional("256GB, 4GB RAM");
		p5.setNumPiezas(2);
		p5.setPrecio(12800.99);
		p5.setVendedor(v5);
		productoService.guardar(p5);
		
		Producto p6 = new Producto();
		p6.setNombre("Corsair Void");
		p6.setDescripcion("Wireless Edition");
		p6.setEstado(1); // activo
		p6.setInformacionAdicional("Nuevo");
		p6.setNumPiezas(2);
		p6.setPrecio(1000.00);
		p6.setVendedor(v8);
		productoService.guardar(p6);
		
		Producto p7 = new Producto();
		p7.setNombre("Logitech G203");
		p7.setDescripcion("Lightspeed ");
		p7.setEstado(1); // activo
		p7.setInformacionAdicional("Nuevo");
		p7.setNumPiezas(2);
		p7.setPrecio(500.50);
		p7.setVendedor(v8);
		productoService.guardar(p7);
		*/

		Cliente c1 = new Cliente();
		c1.setNombre("Alan");
		c1.setAppaterno("Calderon");
		c1.setApmaterno("Aranda");
		c1.setUsername("alacalara");
		c1.setCorreo("alancalderon1@hotmail.com");
		c1.setContrasena("12345");
		c1.setEstadoActivo(true);
		clienteService.guardar(c1);

		Cliente c2 = new Cliente();
		c2.setNombre("Brandon");
		c2.setAppaterno("Calderon");
		c2.setApmaterno("Aranda");
		c2.setUsername("branyonas");
		c2.setCorreo("jonbran1@hotmail.com");
		c2.setContrasena("bebu123");
		c2.setEstadoActivo(true);
		clienteService.guardar(c2);
		
		Cliente c3 = new Cliente();
		c3.setNombre("Jose");
		c3.setAppaterno("Aranda");
		c3.setApmaterno("Ramirez");
		c3.setUsername("jnash");
		c3.setCorreo("jnash@hotmail.com");
		c3.setContrasena("joseram1");
		c3.setEstadoActivo(true);
		clienteService.guardar(c3);
		
		Cliente c4 = new Cliente();
		c4.setNombre("Javier");
		c4.setAppaterno("Sanchez");
		c4.setApmaterno("Rodriguez");
		c4.setUsername("jchicharito");
		c4.setCorreo("chhernandez@hotmail.com");
		c4.setContrasena("ch1299");
		c4.setEstadoActivo(true);
		clienteService.guardar(c4);
		
		Cliente c5 = new Cliente();
		c5.setNombre("Omar");
		c5.setAppaterno("Turitzo");
		c5.setApmaterno("Rodriguez");
		c5.setUsername("omturo");
		c5.setCorreo("omturo@hotmail.com");
		c5.setContrasena("ch1299");
		c5.setEstadoActivo(true);
		clienteService.guardar(c5);
		
		Cliente c6 = new Cliente();
		c6.setNombre("Javier");
		c6.setAppaterno("Sanchez");
		c6.setApmaterno("Rodriguez");
		c6.setUsername("jchicharito");
		c6.setCorreo("chhernandez@hotmail.com");
		c6.setContrasena("ch1299");
		c6.setEstadoActivo(true);
		clienteService.guardar(c6);

		Pedido ped1 = new Pedido();
		ped1.setEstado(0);
		ped1.setInfoAdicional("En camino");
		ped1.setTotalPagar(450.5);
		ped1.setCliente(c1);
		pedidoService.guardar(ped1);

		Pedido ped2 = new Pedido();
		ped2.setEstado(0);
		ped2.setInfoAdicional("Preventa");
		ped2.setTotalPagar(600.0);
		ped2.setCliente(c1);
		pedidoService.guardar(ped2);

		Pedido ped3 = new Pedido();
		ped3.setEstado(0);
		ped3.setInfoAdicional("Ya casi llega");
		ped3.setTotalPagar(950.0);
		ped3.setCliente(c2);
		pedidoService.guardar(ped3);

		Pedido ped4 = new Pedido();
		ped4.setEstado(1);
		ped4.setInfoAdicional("Entregar despues de las 12:pm ");
		ped4.setTotalPagar(680.0);
		ped4.setCliente(c1);
		pedidoService.guardar(ped4);
		System.out.println("PRODUCTOS:" + productoService.obtenerTodos());
		
		Pago pago1 = new Pago();
		pago1.setCliente(c1);
		pago1.setTitular("Fidel Aranda Ramirez");
		pago1.setCCV("123");
		pago1.setNumTarjeta("5415901951152435");
		pagoService.guardar(pago1);
		
		Pago pago2 = new Pago();
		pago2.setCliente(c1);
		pago2.setTitular((c1.getNombre().concat(" "+c1.getAppaterno())).concat(" "+c1.getApmaterno()));
		//pago2.setPedido(ped1);
		pago2.setCCV("2399");
		pago2.setNumTarjeta("5415901951155545");
		pagoService.guardar(pago2);
		
		Pago pago4 = new Pago();
		pago4.setCliente(c2);
		pago4.setTitular((c2.getNombre().concat(" "+c2.getAppaterno())).concat(" "+c2.getApmaterno()));
		//pago4.setPedido(ped2);
		pago4.setCCV("2399");
		pago4.setNumTarjeta("5415901951156873");
		pagoService.guardar(pago4);
		
		Pago pago5 = new Pago();
		pago5.setCliente(c3);
		pago5.setTitular((c3.getNombre().concat(" "+c3.getAppaterno())).concat(" "+c3.getApmaterno()));
		//pago5.setPedido(ped1);
		pago5.setCCV("2399");
		pago5.setNumTarjeta("5415901951156873");
		pagoService.guardar(pago5);
		
		//pago.setFechaExpiracion("");
	}
}