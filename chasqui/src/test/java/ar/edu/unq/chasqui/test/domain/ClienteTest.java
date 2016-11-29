package ar.edu.unq.chasqui.test.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.chasqui.exceptions.DireccionesInexistentes;
import ar.edu.unq.chasqui.exceptions.PedidoInexistenteException;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Direccion;
import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.model.ProductoPedido;
import ar.edu.unq.chasqui.model.Variante;
import ar.edu.unq.chasqui.service.rest.request.ConfirmarPedidoRequest;
import ar.edu.unq.chasqui.service.rest.request.DireccionRequest;
import ar.edu.unq.chasqui.view.composer.Constantes;



public class ClienteTest {
	
	Cliente cliente;
	Cliente clienteSinDireccionPredeterminada;
	Direccion nuevaDireccion;
	Direccion direccionPredeterminada;
	DireccionRequest dirRequest;
	Pedido pedido;
	Pedido pedidoNoVigente;
	Pedido pedidoAgregar;
	Variante v;
	ConfirmarPedidoRequest request;
	
	
	@Before
	public void setUp(){
		cliente = new Cliente();
		cliente.setNombre("nombre");
		cliente.setApellido("apellido");
		cliente.setNickName("nickname");
		cliente.setEmail("email");
		cliente.setTelefonoFijo("43434343");
		cliente.setTelefonoMovil("1414141414141");
		
		clienteSinDireccionPredeterminada = new Cliente();
		clienteSinDireccionPredeterminada.setNombre("nombre");
		clienteSinDireccionPredeterminada.setApellido("apellido");
		clienteSinDireccionPredeterminada.setNickName("nickname");
		clienteSinDireccionPredeterminada.setEmail("email");
		clienteSinDireccionPredeterminada.setTelefonoFijo("43434343");
		clienteSinDireccionPredeterminada.setTelefonoMovil("1414141414141");
		clienteSinDireccionPredeterminada.setDireccionesAlternativas(new ArrayList<Direccion>());
		
		
		dirRequest = new DireccionRequest();
		dirRequest.setAlias("aliasreq");
		dirRequest.setAltura(1231);
		dirRequest.setCalle("callereq");
		dirRequest.setPredeterminada(true);
		dirRequest.setDepartamento("4d");
		dirRequest.setLocalidad("avellanedareq");
		
		List<Direccion> direccionesAlternativas = new ArrayList<Direccion>();
		direccionPredeterminada = new Direccion();
		direccionPredeterminada.setAlias("alias");
		direccionPredeterminada.setId(1);
		direccionPredeterminada.setAltura(123);
		direccionPredeterminada.setCalle("calle");
		direccionPredeterminada.setCodigoPostal("1231");
		direccionPredeterminada.setDepartamento("44s");
		direccionPredeterminada.setPredeterminada(true);
		direccionesAlternativas.add(direccionPredeterminada);
		
		nuevaDireccion = new Direccion();
		nuevaDireccion.setAlias("alias");
		nuevaDireccion.setAltura(123);
		nuevaDireccion.setCalle("calle");
		nuevaDireccion.setCodigoPostal("1231");
		nuevaDireccion.setDepartamento("44s");
		nuevaDireccion.setPredeterminada(false);
		cliente.setDireccionesAlternativas(direccionesAlternativas);		
		List<Pedido>pss = new ArrayList<Pedido>();
		pedido = new Pedido();
		pedido.setMontoActual(0.0);
		pedido.setProductosEnPedido(new HashSet<ProductoPedido>());
		pedido.setEstado(Constantes.ESTADO_PEDIDO_ABIERTO);
		pedido.setFechaCreacion(new DateTime());
		pedido.setIdVendedor(1);
		pedido.setId(1);
		pedido.setFechaDeVencimiento(new DateTime().plusMonths(12));
		
		pedidoNoVigente = new Pedido();
		pedidoNoVigente.setEstado(Constantes.ESTADO_PEDIDO_ABIERTO);
		pedidoNoVigente.setFechaCreacion(new DateTime());
		pedidoNoVigente.setIdVendedor(2);
		pedidoNoVigente.setFechaDeVencimiento(new DateTime().plusMonths(-12));
		pedidoNoVigente.setId(2);
		pss.add(pedido);
		pss.add(pedidoNoVigente);
		cliente.setPedidos(pss);
		pedidoAgregar = new Pedido();
		
		v = new Variante();
		v.setId(1);
		v.setNombre("frutilla");
		v.setPrecio(4.33);
		v.setStock(10);
		Producto p = new Producto();
		p.setNombre("mermelada");
		v.setProducto(p);
		List<Imagen>img = new ArrayList<Imagen>();
		Imagen i = new Imagen();
		i.setPath("path");
		img.add(i);
		v.setImagenes(img);
		request = new ConfirmarPedidoRequest();
		request.setIdPedido(1);
		request.setIdDireccion(1);
		
	}

	@Test
	public void testObtenerDireccionDetenerminada() {
		assertEquals(cliente.obtenerDireccionPredeterminada(),direccionPredeterminada);
	}
	
	@Test
	public void testClienteSinDireccionPredeterminada(){
		assertNull(clienteSinDireccionPredeterminada.obtenerDireccionPredeterminada());
	}
	
	@Test
	public void testagregarDireccion(){
		cliente.agregarDireccion(dirRequest);
		assertEquals(cliente.getDireccionesAlternativas().size(), 2);
		assertEquals(cliente.obtenerDireccionPredeterminada().getAlias(), "aliasreq");
	}
	
	@Test
	public void testEditarDireccion() throws DireccionesInexistentes{
		cliente.editarDireccionCon(dirRequest, 1);
		assertEquals(cliente.obtenerDireccionPredeterminada().getAlias(),"aliasreq");
		assertEquals(cliente.getDireccionesAlternativas().size(),1);
	}
	
	@Test(expected=DireccionesInexistentes.class)
	public void testEditarDireccionInexistente() throws DireccionesInexistentes{
		cliente.editarDireccionCon(dirRequest, 4);
	}
	
	@Test
	public void testEliminarDireccion() throws DireccionesInexistentes{
		cliente.eliminarDireccion(1);
		assertEquals(cliente.getDireccionesAlternativas().size(),0);
	}
	
	@Test(expected=DireccionesInexistentes.class)
	public void testEliminarDireccionInexistente() throws DireccionesInexistentes{
		cliente.eliminarDireccion(4);
		assertEquals(cliente.getDireccionesAlternativas().size(),0);
	}
	
	@Test
	public void testObtenerPedido() throws PedidoInexistenteException{
		assertEquals(cliente.obtenerPedidoActualDe(1),pedido);
	}
	
	@Test(expected=PedidoInexistenteException.class)
	public void testObtenerPedidoVendedorInexistente() throws PedidoInexistenteException{
		cliente.obtenerPedidoActualDe(4);
	}
	
	@Test(expected=PedidoInexistenteException.class)
	public void testObtenerPedidoVendedorNoVigente() throws PedidoInexistenteException{
		cliente.obtenerPedidoActualDe(2);
	}
	
	@Test
	public void testAgregarPedido(){
		cliente.agregarPedido(pedidoAgregar);
		assertEquals(cliente.getPedidos().size(), 3);
	}
	
	@Test
	public void testContienePedidoVigente(){
		assertTrue(cliente.contienePedidoVigente(1));
	}
	
	@Test
	public void testNoContienePedidoVigente(){
		assertFalse(cliente.contienePedidoVigente(2));
	}
	
	@Test
	public void testVarianteCorrespondeConPedido(){
		assertTrue(cliente.varianteCorrespondeConPedido(1, 1));
	}
	
	@Test
	public void testVarianteNoCorrespondeConIdPedido(){
		assertFalse(cliente.varianteCorrespondeConPedido(1, 2));
	}
	
	@Test
	public void testAgregarProductoAPedido() throws PedidoInexistenteException{
		cliente.agregarProductoAPedido(v, 1, 1);
		assertEquals(cliente.obtenerPedidoActualDe(1).getProductosEnPedido().size(),1);
		assertEquals(cliente.obtenerPedidoActualDe(1).getMontoActual(),new Double(4.33));
	}
	
	@Test
	public void testNoContieneProductoEnPedido(){
		assertFalse(cliente.contieneProductoEnPedido(v, 1));
	}
	
	@Test
	public void testContieneProductoEnPedido(){
		cliente.agregarProductoAPedido(v, 1, 1);
		assertTrue(cliente.contieneProductoEnPedido(v, 1));
	}
	
	
	@Test
	public void testContieneCantidadDeProductoEnPedido(){
		cliente.agregarProductoAPedido(v, 1, 1);
		assertTrue(cliente.contieneCantidadDeProductoEnPedido(v,1, 1));
	}
	
	@Test
	public void testNoContieneCantidadDeProductoEnPedido(){
		cliente.agregarProductoAPedido(v, 1, 1);
		assertFalse(cliente.contieneCantidadDeProductoEnPedido(v,1, 4));
	}
	
	@Test
	public void testEliminarProductoEnPedido() throws PedidoInexistenteException{
		cliente.agregarProductoAPedido(v, 1, 1);
		cliente.eliminarProductoEnPedido(v.getId(), v.getPrecio(), 1, 1);
		assertEquals(cliente.obtenerPedidoActualDe(1).getProductosEnPedido().size(),0);
	}
	
	@Test
	public void testEliminarCantidadDeProductoEnPedido() throws PedidoInexistenteException{
		cliente.agregarProductoAPedido(v, 1, 2);
		cliente.eliminarProductoEnPedido(v.getId(), v.getPrecio(), 1, 1);
		assertEquals(cliente.obtenerPedidoActualDe(1).getProductosEnPedido().size(),1);
	}
	
	@Test
	public void testEliminarPedido(){
		cliente.eliminarPedido(1);
		assertFalse(cliente.contienePedidoVigente(1));
	}
	
	@Test
	public void testConfirmarPedido(){
		cliente.confirmarPedido(request);
		assertEquals(cliente.getPedidos().size(),1);
		assertTrue(cliente.getHistorialPedidos() != null);
		assertEquals(cliente.getHistorialPedidos().getPedidos().size(),1);
	}
	
	@Test
	public void testContieneDireccionConId(){
		assertTrue(cliente.contieneDireccion(1));
	}

}
