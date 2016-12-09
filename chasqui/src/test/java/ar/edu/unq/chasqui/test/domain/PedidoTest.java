package ar.edu.unq.chasqui.test.domain;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.model.ProductoPedido;
import ar.edu.unq.chasqui.view.composer.Constantes;

public class PedidoTest {

	
	Pedido pedido;
	Pedido pedidoInvalido;
	Pedido pedidoInvalidoFecha;
	Pedido pedidoAbierto;
	ProductoPedido pp;
	
	@Before
	public void setUp() {
		
		pedido = new Pedido();
		pedidoInvalido = new Pedido();
		pedidoAbierto = new Pedido();
		pedidoInvalidoFecha = new Pedido();
		Set<ProductoPedido>ps = new HashSet<ProductoPedido>();
		
		pedido.setAlterable(true);
		pedido.setEstado(Constantes.ESTADO_PEDIDO_CONFIRMADO);
		pedido.setFechaDeVencimiento(new DateTime().plusMonths(-2));
		
		pedidoInvalido.setAlterable(true);
		pedidoInvalido.setEstado(Constantes.ESTADO_PEDIDO_CANCELADO);
		pedidoInvalido.setFechaDeVencimiento(new DateTime().plusMonths(-2));
		
		pedidoInvalidoFecha.setAlterable(true);
		pedidoInvalidoFecha.setEstado(Constantes.ESTADO_PEDIDO_ABIERTO);
		pedidoInvalidoFecha.setFechaDeVencimiento(new DateTime().plusMonths(-2));
		pedidoAbierto.setProductosEnPedido(ps);
		
		pedidoAbierto.setAlterable(true);
		pedidoAbierto.setEstado(Constantes.ESTADO_PEDIDO_ABIERTO);
		pedidoAbierto.setFechaDeVencimiento(new DateTime().plusMonths(2));
		pedidoAbierto.setMontoActual(0.0);
		
		pp = new ProductoPedido();
		pp.setId(1);
		pp.setIdVariante(4);
		pp.setImagen("a");
		pp.setNombreProducto("nombre");
		pp.setNombreVariante("variante");
		pp.setPrecio(10.0);
		pp.setCantidad(1);
	}

	@Test
	public void testConfirmate() {
		pedido.confirmarte();
		assertEquals(Constantes.ESTADO_PEDIDO_ENTREGADO,pedido.getEstado());
		assertFalse(pedido.getAlterable());
	}
	
	@Test
	public void testConfirmatePedidoAbierto() {
		pedidoAbierto.confirmarte();
		assertEquals(Constantes.ESTADO_PEDIDO_ABIERTO,pedidoAbierto.getEstado());
		assertTrue(pedidoAbierto.getAlterable());
	}
	
	@Test
	public void testConfirmatePedidoCancelado() {
		pedidoInvalido.confirmarte();
		assertEquals(Constantes.ESTADO_PEDIDO_CANCELADO,pedidoInvalido.getEstado());
		assertTrue(pedido.getAlterable());
	}
	
	
	@Test
	public void testVigente() {
		assertFalse(pedido.estaVigente());
	}
	
	@Test
	public void testVigenteAbierto() {
		assertTrue(pedidoAbierto.estaVigente());
	}
	
	@Test
	public void testVigenteCancelado() {
		assertFalse(pedidoInvalido.estaVigente());
	}
	
	@Test
	public void testVigenteInvalidoFecha() {
		assertFalse(pedidoInvalidoFecha.estaVigente());
	}
	
	@Test
	public void testPedidoAbiertoAgregarProducto(){
		assertEquals(pedidoAbierto.getProductosEnPedido().size(),0);
		pedidoAbierto.agregarProductoPedido(pp);
		assertEquals(pp.getCantidad(), new Integer(1));
		assertEquals(pedidoAbierto.getProductosEnPedido().size(),1);
	}
	
	@Test
	public void testPedidoAbiertoAgregarProductoYaExistente(){
		assertEquals(pedidoAbierto.getProductosEnPedido().size(),0);
		pedidoAbierto.agregarProductoPedido(pp);
		pedidoAbierto.agregarProductoPedido(pp);
		assertEquals(pp.getCantidad(), new Integer(2));
		assertEquals(pedidoAbierto.getProductosEnPedido().size(),1);
	}
	
	@Test
	public void testPedidoAbiertoEliminarProducto(){
		pedidoAbierto.agregarProductoPedido(pp);
		assertEquals(pedidoAbierto.getProductosEnPedido().size(),1);
		pedidoAbierto.eliminar(pp);
		assertEquals(pedidoAbierto.getProductosEnPedido().size(),0);
	}
	
	@Test
	public void testPedidoAbiertoSumarAlMontoActual(){
		pedidoAbierto.sumarAlMontoActual(10.0, 2);
		assertEquals(pedidoAbierto.getMontoActual(),new Double(20.0));
	}
	
	@Test
	public void testPedidoAbiertoRestarAlMontoActual(){
		pedidoAbierto.sumarAlMontoActual(10.0, 2);
		pedidoAbierto.restarAlMontoActual(10.0, 1);
		assertEquals(pedidoAbierto.getMontoActual(),new Double(10.0));
	}

}
