package ar.edu.unq.chasqui.test.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.unq.chasqui.exceptions.DireccionesInexistentes;
import ar.edu.unq.chasqui.exceptions.RequestIncorrectoException;
import ar.edu.unq.chasqui.exceptions.UsuarioExistenteException;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Direccion;
import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.model.ProductoPedido;
import ar.edu.unq.chasqui.service.rest.request.DireccionEditRequest;
import ar.edu.unq.chasqui.service.rest.request.DireccionRequest;
import ar.edu.unq.chasqui.service.rest.request.EditarPerfilRequest;
import ar.edu.unq.chasqui.services.interfaces.PedidoService;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;
import ar.edu.unq.chasqui.view.composer.Constantes;

@ContextConfiguration(locations = {
"file:src/test/java/dataSource-Test.xml",
"file:src/main/resources/beans/service-beans.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UsuarioServiceTest extends GenericSetUp{

	
	@Autowired UsuarioService usuarioService;
	@Autowired PedidoService pedidoService;
	EditarPerfilRequest edicionUsuario;
	DireccionRequest direccionRequest;
	DireccionEditRequest editRequest;
	Cliente c;

	
	
	
	
	@Before
	public void setUp() throws Exception{
		

		Pedido p = new Pedido();
		DateTime dt = new DateTime();
		p.setAlterable(true);
		ProductoPedido pp = new ProductoPedido();
		pp.setCantidad(4);
		pp.setIdVariante(1);
		pp.setNombreProducto("hola");
		pp.setNombreVariante("a");
		pp.setPrecio(44.4);
		p.setProductosEnPedido(new HashSet<ProductoPedido>());
		p.agregarProductoPedido(pp);
		p.setMontoActual(40.4);
		p.setMontoMinimo(50.5);
		p.setFechaCreacion(new DateTime());
		p.setFechaDeVencimiento(dt.plusDays(10));
		p.setIdVendedor(2);
		p.setEstado(Constantes.ESTADO_PEDIDO_ABIERTO);
		p.setPerteneceAPedidoGrupal(false);
		p.setUsuarioCreador("jfflores90@gmai");

		
		c = new Cliente();
		c.setToken("federico");
		c.setPassword(encrypter.encrypt("federico"));
		c.setEmail("jfflores90@gmail.com");
		c.setNombre("JORGE");
		c.setApellido("Flores");
		c.setTelefonoFijo("12314124");
		c.setTelefonoMovil("1234214124");
		c.setNickName("MatLock");
		Direccion d = new Direccion();
		d.setAlias("CASA");
		d.setCalle("calle");
		d.setAltura(1234);
		d.setCodigoPostal("1234");
		d.setDepartamento("4D");
		d.setPredeterminada(true);
		p.setDireccionEntrega(d);

		pedidoService.guardar(p);
		List<Direccion> dds = new ArrayList<Direccion>();
		List<Pedido>pss = new ArrayList<Pedido>();
		dds.add(d);
		c.setPedidos(pss);
		c.setDireccionesAlternativas(dds);
		c.getPedidos().add(p);
		
		edicionUsuario = new EditarPerfilRequest();
		edicionUsuario.setApellido("nuevoApellido");
		edicionUsuario.setNombre("nuevoNombre");
		edicionUsuario.setTelefonoFijo("12345678");
		
		
		direccionRequest = new DireccionRequest();
		direccionRequest.setAlias("Trabajo");
		direccionRequest.setAltura(123456);
		direccionRequest.setCalle("trabajo");
		direccionRequest.setCodigoPostal("312dsaw");
		direccionRequest.setLocalidad("Avellaneda");
		direccionRequest.setPredeterminada(false);
		
		
		editRequest = new DireccionEditRequest();
		editRequest.setAlias("nuevoAlias");
		editRequest.setCalle("nuevaCalle");
		editRequest.setAltura(1234);
		editRequest.setCodigoPostal("1234");
		editRequest.setPredeterminada(true);
		editRequest.setLocalidad("Avellaneda");
		
		usuarioService.guardarUsuario(c);
		editRequest.setIdDireccion(d.getId());
	}
	
	
	@After
	public void tearDown(){
		usuarioService.deleteObject(c);
	}
	
	
	@Test
	public void testObtenerUsuarioPorEmail(){
		Cliente c2 = usuarioService.obtenerClienteConDireccion("jfflores90@gmail.com");
		assertEquals(c2.getEmail(),c.getEmail());
	}
	
	@Test(expected=UsuarioExistenteException.class)
	public void testObtenerUsuarioInvalidoPorEmail(){
		Cliente c2 = usuarioService.obtenerClienteConDireccion("sarasa@gmail.com");
		assertEquals(c2.getEmail(),c.getEmail());
	}
	
	
	@Test
	public void testEditarPerfilUsuario() throws Exception{
		usuarioService.modificarUsuario(edicionUsuario,"jfflores90@gmail.com");
		Cliente c2 = usuarioService.obtenerClienteConDireccion("jfflores90@gmail.com");
		assertEquals(c2.getNickName(),"MatLock");
		assertEquals(c2.getNombre(),"nuevoNombre");
		assertEquals(c2.getApellido(),"nuevoApellido");
	}
	
	@Test(expected=UsuarioExistenteException.class)
	public void testEditarPerfilUsuarioInexistente() throws Exception{
		usuarioService.modificarUsuario(edicionUsuario,"sarasa@gmail.com");
	}
	
	
	@Test
	public void testObtenerDireccionesUsuario() throws Exception{
		usuarioService.modificarUsuario(edicionUsuario,"jfflores90@gmail.com");
		List<Direccion> ds = usuarioService.obtenerDireccionesDeUsuarioCon("jfflores90@gmail.com");
		assertTrue(null != ds);
		assertEquals(ds.get(0).getCalle(),"calle");
	}
	
	@Test(expected=UsuarioExistenteException.class)
	public void testObtenerDireccionesDeUsuarioInexistente() throws Exception{
		usuarioService.obtenerDireccionesDeUsuarioCon("sarasa@gmail.com");
	}
	
	
	@Test
	public void testAgregarNuevaDireccionUsuario(){
		usuarioService.agregarDireccionAUsuarioCon("jfflores90@gmail.com", direccionRequest);
		Cliente c2 = usuarioService.obtenerClienteConDireccion("jfflores90@gmail.com");
		assertTrue(c2.getDireccionesAlternativas() != null);
		assertEquals(c2.getDireccionesAlternativas().size(),2);
	}
	
	@Test(expected=UsuarioExistenteException.class)
	public void testAgregarNuevaDireccionUsuarioInexistente(){
		usuarioService.agregarDireccionAUsuarioCon("sarasa@gmail.com", direccionRequest);
	}
	
	@Test(expected=RequestIncorrectoException.class)
	public void testAgregarNuevaDireccionUsuarioCalleNull(){
		direccionRequest.setCalle(null);
		usuarioService.agregarDireccionAUsuarioCon("jfflores90@gmail.com", direccionRequest);
	}
	
	@Test(expected=RequestIncorrectoException.class)
	public void testAgregarNuevaDireccionUsuarioAliasNull(){
		direccionRequest.setAlias(null);
		usuarioService.agregarDireccionAUsuarioCon("jfflores90@gmail.com", direccionRequest);
	}
	
	@Test(expected=RequestIncorrectoException.class)
	public void testAgregarNuevaDireccionUsuarioCodigoPostalNull(){
		direccionRequest.setCodigoPostal(null);
		usuarioService.agregarDireccionAUsuarioCon("jfflores90@gmail.com", direccionRequest);
	}
	
	@Test(expected=RequestIncorrectoException.class)
	public void testAgregarNuevaDireccionUsuarioDirPredeterminadaNull(){
		direccionRequest.setPredeterminada(null);
		usuarioService.agregarDireccionAUsuarioCon("jfflores90@gmail.com", direccionRequest);
	}
	
	@Test(expected=RequestIncorrectoException.class)
	public void testAgregarNuevaDireccionUsuarioLocalidadNull(){
		direccionRequest.setLocalidad(null);
		usuarioService.agregarDireccionAUsuarioCon("jfflores90@gmail.com", direccionRequest);
	}
	
	@Test(expected=RequestIncorrectoException.class)
	public void testAgregarNuevaDireccionUsuarioAlturaNull(){
		direccionRequest.setAltura(null);
		usuarioService.agregarDireccionAUsuarioCon("jfflores90@gmail.com", direccionRequest);
	}
	
	@Test(expected=RequestIncorrectoException.class)
	public void testAgregarNuevaDireccionUsuarioAlturaNegativa(){
		direccionRequest.setAltura(-10);
		usuarioService.agregarDireccionAUsuarioCon("jfflores90@gmail.com", direccionRequest);
	}
	
	
	@Test
	public void testEditarDireccionUsuario() throws UsuarioExistenteException, DireccionesInexistentes{
		usuarioService.editarDireccionDe("jfflores90@gmail.com",editRequest,editRequest.getIdDireccion());
		Cliente c2 = usuarioService.obtenerClienteConDireccion("jfflores90@gmail.com");
		assertTrue(c2.getDireccionesAlternativas() != null);
		assertEquals(c2.getDireccionesAlternativas().size(),1);
		assertEquals(c2.getDireccionesAlternativas().get(0).getCalle(),editRequest.getCalle());
	}
	
	@Test(expected=UsuarioExistenteException.class)
	public void testEditarDireccionUsuarioInexistente() throws UsuarioExistenteException, DireccionesInexistentes{
		usuarioService.editarDireccionDe("sarasa@gmail.com",editRequest,editRequest.getIdDireccion());
	}
	
	@Test(expected=UsuarioExistenteException.class)
	public void testEditarDireccionUsuarioDireccionInexistente() throws UsuarioExistenteException, DireccionesInexistentes{
		usuarioService.editarDireccionDe("sarasa@gmail.com",editRequest,15);
	}
	
	@Test(expected=DireccionesInexistentes.class)
	public void testEditarDireccionUsuarioDireccionNull() throws UsuarioExistenteException, DireccionesInexistentes{
		usuarioService.editarDireccionDe("jfflores90@gmail.com",editRequest,null);
	}
	
	
	
}
