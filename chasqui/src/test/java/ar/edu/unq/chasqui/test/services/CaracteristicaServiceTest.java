package ar.edu.unq.chasqui.test.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.CaracteristicaProductor;

@ContextConfiguration(locations = {
"file:src/test/java/dataSource-Test.xml",
"file:src/main/resources/beans/service-beans.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CaracteristicaServiceTest extends GenericSetUp{

	
	@Before
	public void setUp() throws Exception{
		super.setUp();
	}
	
	@After
	public void tearDown(){
		super.tearDown();
	}
	
	
	@Test
	public void testBuscarCaracteristicasProducto(){
		List<Caracteristica> cs = caracteristicaService.buscarCaracteristicasProducto();
		assertTrue(cs != null );
		assertEquals(cs.get(0).getNombre(),"caracteristica");
	}
	
	@Test
	public void testBuscarCaracteristicasProductor(){
		List<CaracteristicaProductor> cs = caracteristicaService.buscarCaracteristicasProductor();
		assertTrue(cs != null );
		assertEquals(cs.get(0).getNombre(),"caracteristicaProductor");
	}
	
	@Test
	public void testActualizarCaracteristica(){
		caracteristica.setNombre("CAMBIO");
		caracteristicaService.actualizarCaracteristica(caracteristica);
		Caracteristica nueva = caracteristicaService.buscarCaracteristicasProducto().get(0);
		assertEquals(nueva.getNombre(), "CAMBIO");
	}
	
	@Test
	public void testActualizarCaracteristicaProductor(){
		caracteristicaProductor.setNombre("PRODUCTOR");
		caracteristicaService.actualizarCaracteristicaProductor(caracteristicaProductor);
		CaracteristicaProductor nueva = caracteristicaService.buscarCaracteristicasProductor().get(0);
		assertEquals(nueva.getNombre(), "PRODUCTOR");
	}
	
}
