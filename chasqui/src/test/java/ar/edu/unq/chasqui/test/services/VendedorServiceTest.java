package ar.edu.unq.chasqui.test.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.unq.chasqui.exceptions.VendedorInexistenteException;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.security.Encrypter;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;
import ar.edu.unq.chasqui.services.interfaces.VendedorService;

@ContextConfiguration(locations = {
"file:src/test/java/dataSource-Test.xml",
"file:src/main/resources/beans/service-beans.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class VendedorServiceTest extends GenericSetUp{

	
	@Autowired VendedorService vendedorService;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
	}
	
	
	@After
	public void tearDown(){
		super.tearDown();
	}
	
	
	@Test
	public void testObtenerVendedores() {
		assertTrue(vendedorService.obtenerVendedores() != null);
	}
	
	
	@Test
	public void testObtenerVendedor(){
		Vendedor v = vendedorService.obtenerVendedor("MatLock");
		assertTrue(v != null);
		assertEquals(v.getNombre(),"MatLock - Nombre");
	}
	
	@Test(expected=VendedorInexistenteException.class)
	public void testObtenerVendedorInexistente(){
		vendedorService.obtenerVendedor("Inexistente");
	}
	
}
