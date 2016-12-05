package ar.edu.unq.chasqui.test.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.unq.chasqui.dao.UsuarioDAO;
import ar.edu.unq.chasqui.exceptions.DireccionesInexistentes;
import ar.edu.unq.chasqui.exceptions.PedidoInexistenteException;
import ar.edu.unq.chasqui.exceptions.PedidoVigenteException;
import ar.edu.unq.chasqui.exceptions.ProductoInexsistenteException;
import ar.edu.unq.chasqui.exceptions.RequestIncorrectoException;
import ar.edu.unq.chasqui.exceptions.UsuarioExistenteException;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Direccion;
import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.model.Variante;
import ar.edu.unq.chasqui.service.rest.request.AgregarQuitarProductoAPedidoRequest;
import ar.edu.unq.chasqui.service.rest.request.DireccionEditRequest;
import ar.edu.unq.chasqui.service.rest.request.DireccionRequest;
import ar.edu.unq.chasqui.service.rest.request.EditarPerfilRequest;
import ar.edu.unq.chasqui.services.interfaces.PedidoService;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;



@ContextConfiguration(locations = {
"file:src/test/java/dataSource-Test.xml",
"file:src/main/resources/beans/service-beans.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UsuarioServiceTest extends GenericSetUp{

	
	@Autowired UsuarioService usuarioService;
	@Autowired PedidoService pedidoService;
	@Autowired UsuarioDAO usuarioDAO;
	Variante variante;
	EditarPerfilRequest edicionUsuario;
	DireccionRequest direccionRequest;
	DireccionEditRequest editRequest;
	AgregarQuitarProductoAPedidoRequest agregarRequest;
	Cliente cliente;

	public static String EMAIL_CLIENTE = "jfflores90@gmail.com";
	
	
	
	@Before
	public void setUp() throws Exception{
		super.setUp();		
		cliente = new Cliente();
		cliente.setToken("federico");
		cliente.setPassword(encrypter.encrypt("federico"));
		cliente.setEmail(EMAIL_CLIENTE);
		cliente.setNombre("JORGE");
		cliente.setApellido("Flores");
		cliente.setTelefonoFijo("12314124");
		cliente.setTelefonoMovil("1234214124");
		cliente.setNickName("MatLock");
		Direccion d = new Direccion();
		d.setAlias("CASA");
		d.setCalle("calle");
		d.setAltura(1234);
		d.setCodigoPostal("1234");
		d.setDepartamento("4D");
		d.setPredeterminada(true);
		
		Producto p = new Producto();
		p.setNombre("Mermelada");
		p.setCategoria(c);
		p.setFabricante(f);
		p.setCaracteristicas(caracs);
		List<Variante>vss = new ArrayList<Variante>();
		p.setVariantes(vss);
		variante = new Variante();
		variante.setNombre("Frutilla");
		variante.setDescripcion("descripcion");
		variante.setDestacado(false);
		variante.setPrecio(10.10);
		variante.setStock(40);
		variante.setCantidadReservada(0);
		variante.setPrecio(13.0);
		variante.setProducto(p);
		vss.add(variante);
		
		List<Imagen>imgs = new ArrayList<Imagen>();
		Imagen i = new Imagen();
		i.setPath("hola");
		imgs.add(i);
		variante.setImagenes(imgs);
		List<Producto>pss = new ArrayList<Producto>();
		pss.add(p);
		c.setProductos(pss);
		
		List<Direccion> dds = new ArrayList<Direccion>();
		dds.add(d);
		cliente.setDireccionesAlternativas(dds);
		
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
		
		agregarRequest = new AgregarQuitarProductoAPedidoRequest();
		agregarRequest.setCantidad(10);
		agregarRequest.setIdPedido(14);
		
		usuarioService.guardarUsuario(cliente);
		usuarioService.guardarUsuario(u2);
		agregarRequest.setIdVariante(variante.getId());
		editRequest.setIdDireccion(d.getId());
	}
	
	
	@After
	public void tearDown(){
		usuarioService.deleteObject(cliente);
		super.tearDown();
	}
	
	
	@Test
	public void testObtenerUsuarioPorEmail(){
		Cliente c2 = usuarioService.obtenerClienteConDireccion(EMAIL_CLIENTE);
		assertEquals(c2.getEmail(),cliente.getEmail());
	}
	
	@Test(expected=UsuarioExistenteException.class)
	public void testObtenerUsuarioInvalidoPorEmail(){
		Cliente c2 = usuarioService.obtenerClienteConDireccion("sarasa@gmail.com");
		assertEquals(c2.getEmail(),cliente.getEmail());
	}
	
	
	@Test
	public void testEditarPerfilUsuario() throws Exception{
		usuarioService.modificarUsuario(edicionUsuario,EMAIL_CLIENTE);
		Cliente c2 = usuarioService.obtenerClienteConDireccion(EMAIL_CLIENTE);
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
		usuarioService.modificarUsuario(edicionUsuario,EMAIL_CLIENTE);
		List<Direccion> ds = usuarioService.obtenerDireccionesDeUsuarioCon(EMAIL_CLIENTE);
		assertTrue(null != ds);
		assertEquals(ds.get(0).getCalle(),"calle");
	}
	
	@Test(expected=UsuarioExistenteException.class)
	public void testObtenerDireccionesDeUsuarioInexistente() throws Exception{
		usuarioService.obtenerDireccionesDeUsuarioCon("sarasa@gmail.com");
	}
	
	
	@Test
	public void testAgregarNuevaDireccionUsuario(){
		usuarioService.agregarDireccionAUsuarioCon(EMAIL_CLIENTE, direccionRequest);
		Cliente c2 = usuarioService.obtenerClienteConDireccion(EMAIL_CLIENTE);
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
		usuarioService.agregarDireccionAUsuarioCon(EMAIL_CLIENTE, direccionRequest);
	}
	
	@Test(expected=RequestIncorrectoException.class)
	public void testAgregarNuevaDireccionUsuarioAliasNull(){
		direccionRequest.setAlias(null);
		usuarioService.agregarDireccionAUsuarioCon(EMAIL_CLIENTE, direccionRequest);
	}
	
	@Test(expected=RequestIncorrectoException.class)
	public void testAgregarNuevaDireccionUsuarioCodigoPostalNull(){
		direccionRequest.setCodigoPostal(null);
		usuarioService.agregarDireccionAUsuarioCon(EMAIL_CLIENTE, direccionRequest);
	}
	
	@Test(expected=RequestIncorrectoException.class)
	public void testAgregarNuevaDireccionUsuarioDirPredeterminadaNull(){
		direccionRequest.setPredeterminada(null);
		usuarioService.agregarDireccionAUsuarioCon(EMAIL_CLIENTE, direccionRequest);
	}
	
	@Test(expected=RequestIncorrectoException.class)
	public void testAgregarNuevaDireccionUsuarioLocalidadNull(){
		direccionRequest.setLocalidad(null);
		usuarioService.agregarDireccionAUsuarioCon(EMAIL_CLIENTE, direccionRequest);
	}
	
	@Test(expected=RequestIncorrectoException.class)
	public void testAgregarNuevaDireccionUsuarioAlturaNull(){
		direccionRequest.setAltura(null);
		usuarioService.agregarDireccionAUsuarioCon(EMAIL_CLIENTE, direccionRequest);
	}
	
	@Test(expected=RequestIncorrectoException.class)
	public void testAgregarNuevaDireccionUsuarioAlturaNegativa(){
		direccionRequest.setAltura(-10);
		usuarioService.agregarDireccionAUsuarioCon(EMAIL_CLIENTE, direccionRequest);
	}
	
	
	@Test
	public void testEditarDireccionUsuario() throws UsuarioExistenteException, DireccionesInexistentes{
		usuarioService.editarDireccionDe(EMAIL_CLIENTE,editRequest,editRequest.getIdDireccion());
		Cliente c2 = usuarioService.obtenerClienteConDireccion(EMAIL_CLIENTE);
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
		usuarioService.editarDireccionDe(EMAIL_CLIENTE,editRequest,null);
	}
	
	
	@Test(expected=UsuarioExistenteException.class)
	public void testCrearPedidoIndividualVendedorInexistente(){
		usuarioService.crearPedidoPara(EMAIL_CLIENTE, 45);
	}

	
	@Test(expected=UsuarioExistenteException.class)
	public void testCrearPedidoIndividualVendedorSinFechaCierre(){
		u2.setFechaCierrePedido(null);
		usuarioService.guardarUsuario(u2);
		usuarioService.crearPedidoPara(EMAIL_CLIENTE, u2.getId());
	}
	

	@Test(expected=UsuarioExistenteException.class)
	public void testCrearPedidoIndividualVendedorSinMontoMinimo(){
		u2.setMontoMinimoPedido(null);
		usuarioService.guardarUsuario(u2);
		usuarioService.crearPedidoPara(EMAIL_CLIENTE, u2.getId());
	}
	

	@Test(expected=PedidoVigenteException.class)
	public void testCrearPedidoIndividualUsuarioYaContienePedido(){
		usuarioService.crearPedidoPara(EMAIL_CLIENTE, u2.getId());
		usuarioService.crearPedidoPara(EMAIL_CLIENTE, u2.getId());
	}
	
	@Test(expected=PedidoVigenteException.class)
	public void testCrearPedidoIndividualUsuarioSinDireccionPredeterminada(){
		cliente.getDireccionesAlternativas().clear();
		usuarioService.guardarUsuario(cliente);
		usuarioService.crearPedidoPara(EMAIL_CLIENTE, u2.getId());
	}
	
	@Test
	public void testCrearPedidoIndividual() throws PedidoInexistenteException{
		usuarioService.crearPedidoPara(EMAIL_CLIENTE, u2.getId());
		Pedido p = usuarioService.obtenerPedidoActualDe(EMAIL_CLIENTE,u2.getId());
		assertEquals(p.getIdVendedor(),u2.getId());
	}
	
	@Test
	public void testAgregarProductoAPedido() throws PedidoInexistenteException{
		usuarioService.crearPedidoPara(EMAIL_CLIENTE,u2.getId());
		Pedido p = usuarioService.obtenerPedidoActualDe(EMAIL_CLIENTE, u2.getId());
		agregarRequest.setIdPedido(p.getId());
		usuarioService.agregarPedidoA(agregarRequest, EMAIL_CLIENTE);
	}
	
	@Test(expected=ProductoInexsistenteException.class)
	public void testAgregarProductoAPedidoVarianteNoExiste(){
		usuarioService.crearPedidoPara(EMAIL_CLIENTE,u2.getId());
		agregarRequest.setIdVariante(14);
		usuarioService.agregarPedidoA(agregarRequest, EMAIL_CLIENTE);
	}
	
	@Test(expected=PedidoVigenteException.class)
	public void testAgregarProductoAPedidoUsuarioNoPoseePedido(){
		usuarioService.agregarPedidoA(agregarRequest, EMAIL_CLIENTE);
	}
	
	@Test(expected=PedidoVigenteException.class)
	public void testAgregarProductoAPedidoVarianteSinStock(){
		usuarioService.crearPedidoPara(EMAIL_CLIENTE,u2.getId());
		agregarRequest.setCantidad(58);
		usuarioService.agregarPedidoA(agregarRequest, EMAIL_CLIENTE);
	}
	
	@Test
	public void testEliminarPedido() throws PedidoInexistenteException{
		usuarioService.crearPedidoPara(EMAIL_CLIENTE, u2.getId());
		Pedido p = usuarioService.obtenerPedidoActualDe(EMAIL_CLIENTE, u2.getId());
		usuarioService.eliminarPedidoPara(EMAIL_CLIENTE, p.getId());
	}
	
	@Test(expected=PedidoVigenteException.class)
	public void testEliminarPedidoNoExistente(){
		usuarioService.eliminarPedidoPara(EMAIL_CLIENTE, u2.getId());
	}
}
 
