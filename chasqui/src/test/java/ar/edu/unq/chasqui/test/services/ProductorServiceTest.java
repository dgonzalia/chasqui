package ar.edu.unq.chasqui.test.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.unq.chasqui.exceptions.VendedorInexistenteException;
import ar.edu.unq.chasqui.model.Fabricante;
import ar.edu.unq.chasqui.services.interfaces.ProductorService;

@ContextConfiguration(locations = {
"file:src/test/java/dataSource-Test.xml",
"file:src/main/resources/beans/service-beans.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductorServiceTest extends GenericSetUp{

	
	@Autowired ProductorService productorService;
	
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
	}
	
	@After
	public void tearDown(){
		super.tearDown();
	}
	
	
	@Test
	public void testObtenerProductoresDe(){
		List<Fabricante> fs = productorService.obtenerProductoresDe(u2.getId());
		assertTrue(fs != null);
		assertEquals(fs.get(0).getNombre(),"fabricante");		
	}
	
	
	@Test(expected=VendedorInexistenteException.class)
	public void testObtenerProductoresDeVendedorInexistente(){
		productorService.obtenerProductoresDe(5);		
	}
	
}
