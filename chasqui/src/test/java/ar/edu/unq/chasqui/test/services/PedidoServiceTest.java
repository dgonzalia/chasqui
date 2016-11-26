package ar.edu.unq.chasqui.test.services;

import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.services.interfaces.PedidoService;
import ar.edu.unq.chasqui.view.composer.Constantes;

@ContextConfiguration(locations = {
"file:src/test/java/dataSource-Test.xml",
"file:src/main/resources/beans/service-beans.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class PedidoServiceTest extends GenericSetUp{

	
	@Autowired PedidoService pedidoService;
	Pedido p;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		
		p = new Pedido();
		p.setAlterable(true);
		p.setEstado(Constantes.ESTADO_PEDIDO_ABIERTO);
		p.setFechaCreacion(new DateTime());
		p.setFechaDeVencimiento(new DateTime().plusDays(2));
		p.setIdVendedor(u2.getId());
		p.setMontoActual(40.0);
		p.setMontoMinimo(200.0);
		p.setUsuarioCreador("jfflores90@gmail.com");
		
		pedidoService.guardar(p);
	}
	
	@After
	public void tearDown(){
		super.tearDown();
	}
	
	
	@Test
	public void testObtenerPedidosDeVendedor(){
		List<Pedido>p = pedidoService.obtenerPedidosDeVendedor(u2.getId());
		assertTrue(p != null);
		assertEquals(p.size(),1);
		assertEquals(p.get(0).getUsuarioCreador(),"jfflores90@gmail.com");
	}
	
	@Test
	public void totalPedidosVendedor(){
		assertEquals(pedidoService.totalPedidosParaVendedor(u2.getId()),1);
	}
	
	@Test
	public void obtenerPedidosDeVendedorCon(){
		List<Pedido>ps = pedidoService.obtenerPedidosDeVendedor(u2.getId(), new DateTime().plusDays(-2).toDate(), new DateTime().plusDays(3).toDate(), Constantes.ESTADO_PEDIDO_ABIERTO);
		assertTrue(ps != null);
		assertEquals(ps.get(0).getUsuarioCreador(),"jfflores90@gmail.com");
	}
	
	@Test
	public void noExistenPedidosDeVendedor(){
		List<Pedido>ps = pedidoService.obtenerPedidosDeVendedor(u2.getId(), new DateTime().plusDays(-2).toDate(), new DateTime().plusDays(3).toDate(), Constantes.ESTADO_PEDIDO_CANCELADO);
		assertEquals(ps.size(),0);
	}
	
}
