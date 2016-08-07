package ar.edu.unq.chasqui.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.hsqldb.lib.StringUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

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
import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.model.Variante;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.security.Encrypter;
import ar.edu.unq.chasqui.security.PasswordGenerator;
import ar.edu.unq.chasqui.service.rest.request.AgregarQuitarProductoAPedidoRequest;
import ar.edu.unq.chasqui.service.rest.request.DireccionRequest;
import ar.edu.unq.chasqui.service.rest.request.EditarPerfilRequest;
import ar.edu.unq.chasqui.service.rest.request.SingUpRequest;
import ar.edu.unq.chasqui.services.interfaces.ProductoService;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioDAO usuarioDAO;
	@Autowired
	private Encrypter encrypter;
	@Autowired
	private PasswordGenerator passwordGenerator;
	@Autowired
	private ProductoService productoService;
	
	public Usuario obtenerUsuarioPorID(Integer id) {
		return usuarioDAO.obtenerUsuarioPorID(id);
	}

	public Vendedor obtenerVendedorPorID(Integer id) {
		return usuarioDAO.obtenerVendedorPorID(id);
	}
	
	public void guardarUsuario(Usuario u) {
		usuarioDAO.guardarUsuario(u);
		
	}
	
	public void modificarPasswordUsuario(String email,String password){
		Usuario u = usuarioDAO.obtenerUsuarioPorEmail(email);
		u.setPassword(password);
		usuarioDAO.guardarUsuario(u);
	}
	
	
	public void onStartUp() throws Exception{
		Usuario u = usuarioDAO.obtenerUsuarioPorNombre("ROOT");
		if(u == null){
			Vendedor user = new Vendedor();
			user.setUsername("ROOT");
			user.setPassword(encrypter.encrypt("chasquiadmin"));
			user.setEmail("jfflores90@gmail");
			user.setIsRoot(true);
			Imagen img = new Imagen();
			img.setNombre("perfil.jpg");
			img.setPath("/imagenes/usuarios/"+user.getUsername()+"/perfil.jpg");
			user.setImagenPerfil(img.getPath());
			usuarioDAO.guardarUsuario(user);	
			
			Vendedor u2 = new Vendedor();
			u2.setUsername("MatLock");
			u2.setPassword(encrypter.encrypt("federico"));
			u2.setEmail("jfflores90@gmail");
			u2.setIsRoot(false);
			u2.setImagenPerfil(img.getPath());
			u2.setMontoMinimoPedido(213);
			u2.setFechaCierrePedido(new DateTime().plusDays(4));
			usuarioDAO.guardarUsuario(u2);	
			
		
			Cliente c = new Cliente();
			c.setToken("federico");
			c.setPassword(encrypter.encrypt("federico"));
			c.setEmail("jfflores90@gmail.com");
			c.setNombre("JORGE");
			c.setNickName("MatLock");
			Direccion d = new Direccion();
			d.setCalle("aaaa");
			d.setAltura(12313);
			d.setAlias("dir1");
			d.setDepartamento("213123");
			d.setPredeterminada(true);
			Direccion dd = new Direccion();
			dd.setCalle("bbbb");
			dd.setAlias("dir2");
			dd.setCodigoPostal("asdsa");
			dd.setLocalidad("ffff");
			dd.setPredeterminada(false);
			dd.setAltura(111);
			List<Direccion> dds = new ArrayList<Direccion>();
			dds.add(dd);
			dds.add(d);
			c.setDireccionesAlternativas(dds);
			usuarioDAO.guardarUsuario(c);
			
		
		}
	}

	public Usuario login(String username, String passwordHashed) throws Exception {
		Usuario usuario = usuarioDAO.obtenerUsuarioPorNombre(username);
		if (usuario != null ){
			String passwordUser = encrypter.decrypt(usuario.getPassword());
			if(passwordUser.equals(passwordHashed)){
				return usuario;				
			}
		}
		throw new Exception("Usuario o Password incorrectos!");
	}

	public void merguear(Vendedor usuario) {
		usuarioDAO.merge(usuario);
		
	}

	public Usuario obtenerUsuarioPorEmail(String email) {
		return usuarioDAO.obtenerUsuarioPorEmail(email);
	}
	
	public boolean existeUsuarioCon(String email){
		return usuarioDAO.existeUsuarioCon(email);
	}

	public Cliente loginCliente(String email, String password) throws Exception {
		Cliente c = (Cliente) obtenerUsuarioPorEmail(email);
		if(c != null){
			String passwordUser = encrypter.decrypt(c.getPassword());
			if(passwordUser.equals(password)){
				String token = passwordGenerator.generateRandomToken();
				c.setToken(token);
				usuarioDAO.guardarUsuario(c);
				return c;				
			}
		}
		throw new UsuarioExistenteException("No existe el usuario");
	}

	public Cliente crearCliente(SingUpRequest request) throws Exception {
		Cliente c = new Cliente(request,passwordGenerator.generateRandomToken());
		usuarioDAO.guardarUsuario(c);
		return c;
	}

	@Override
	public void modificarUsuario(EditarPerfilRequest editRequest,String email) throws Exception {
		Cliente c = (Cliente) usuarioDAO.obtenerUsuarioPorEmail(email);
		if(c == null){
			throw new UsuarioExistenteException("No existe el usuario");
		}
		if(!StringUtil.isEmpty(editRequest.getPassword())){
			editRequest.setPassword(encrypter.encrypt(editRequest.getPassword()));
		}
		c.modificarCon(editRequest);
		usuarioDAO.guardarUsuario(c);
	}

	@Override
	public void agregarDireccionAUsuarioCon(String mail, DireccionRequest request) {
		Cliente v = (Cliente) usuarioDAO.obtenerUsuarioPorEmail(mail);
		v.agregarDireccion(request);
		usuarioDAO.guardarUsuario(v);
		
	}

	@Override
	public void inicializarListasDe(Vendedor usuarioLogueado) {
		usuarioDAO.inicializarListasDe(usuarioLogueado);
		
	}
	

	@Override
	public Cliente obtenerClienteConDireccion(String mail){
		Cliente c = usuarioDAO.obtenerClienteConDireccionPorEmail(mail);
		if(c == null){
			throw new UsuarioExistenteException("No se ha encontrado el usuario con el mail otorgado");
		}
		return c;
	}

	@Override
	public List<Direccion> obtenerDireccionesDeUsuarioCon(String mail) throws DireccionesInexistentes {
		Cliente c = usuarioDAO.obtenerClienteConDireccionPorEmail(mail);
		if(c == null){
			throw new UsuarioExistenteException("No se ha encontrado el usuario con el mail otorgado");
		}
		return c.getDireccionesAlternativas();
	}

	@Override
	public void editarDireccionDe(String mail,DireccionRequest request,Integer idDireccion) throws DireccionesInexistentes, UsuarioExistenteException {
		Cliente c = (Cliente)usuarioDAO.obtenerUsuarioPorEmail(mail);
		if(c == null){
			throw new UsuarioExistenteException("No existe el usuario");
		}
		c.editarDireccionCon(request,idDireccion);
		usuarioDAO.guardarUsuario(c);
	}

	@Override
	public void eliminarDireccionDe(String mail, Integer idDireccion)
			throws DireccionesInexistentes, UsuarioExistenteException {
		Cliente c = (Cliente) usuarioDAO.obtenerUsuarioPorEmail(mail);
		if(c == null){
			throw new UsuarioExistenteException("No existe el usuario");
		}
		c.eliminarDireccion(idDireccion);
		usuarioDAO.guardarUsuario(c);
		
	}

	@Override
	public Pedido obtenerPedidoActualDe(String mail,Integer idVendedor) throws PedidoInexistenteException {
		return ( (Cliente) usuarioDAO.obtenerUsuarioPorEmail(mail)).obtenerPedidoActualDe(idVendedor);
	}

	@Override
	public void crearPedidoPara(String mail,Integer idVendedor) {
		Cliente c = usuarioDAO.obtenerClienteConPedido(mail);
		Vendedor v = usuarioDAO.obtenerVendedorPorID(idVendedor);
		if(c == null){
			throw new UsuarioExistenteException("No se ha encontrado el usuario con el mail otorgado");
		}
		if(v == null || v.getIsRoot() ){
			throw new UsuarioExistenteException("Vendedor Inexistente");
		}
		if(c.contienePedidoVigenteParaVendedor(idVendedor)){
			throw new PedidoVigenteException("El usuario: "+ c.getNickName() +" ya posee un pedido vigente para el vendedor brindado");
		}
		Pedido p = new Pedido(v,c.getEmail());
		c.agregarPedido(p);
		usuarioDAO.guardarUsuario(c);
		
	}

	@Override
	public void agregarPedidoA(AgregarQuitarProductoAPedidoRequest request, String email) {
		Cliente c = usuarioDAO.obtenerClienteConPedido(email);
		Variante v = productoService.obtenerVariantePor(request.getIdVariante());
		validar(v,c,request);
		c.agregarProductoAPedido(v,request.getIdPedido(),request.getCantidad());
		usuarioDAO.guardarUsuario(c);
		v.reservarCantidad(request.getCantidad());
		productoService.modificarVariante(v);		
	}

	private void validar(Variante v, Cliente c,AgregarQuitarProductoAPedidoRequest request) {		
		validacionesGenerales(v,c,request);		
		if(!v.tieneStockParaReservar(request.getCantidad())){
			throw new ProductoInexsistenteException("El producto no posee más Stock");
		}	
	}
	
	private void validacionesGenerales(Variante v, Cliente c, AgregarQuitarProductoAPedidoRequest request){
		if(v == null){
			throw new ProductoInexsistenteException("No existe el producto con ID: "+ request.getIdVariante());
		}
		if(!c.contienePedidoVigente(request.getIdPedido())){
			throw new PedidoVigenteException("El usuario no posee el pedido con ID:" + request.getIdPedido()+" o el mismo no se encuentra vigente");
		}
		
		if(!c.varianteCorrespondeConPedido(v.getIdVendedor(),request.getIdPedido())){
			throw new RequestIncorrectoException("El producto no corresponde con el vendedor al que se le hizo el pedido con ID: "+ request.getIdPedido());
		}		
	}

	@Override
	public void eliminarProductoDePedido(AgregarQuitarProductoAPedidoRequest request, String email) {
		Cliente c = usuarioDAO.obtenerClienteConPedido(email);
		Variante v = productoService.obtenerVariantePor(request.getIdVariante());
		validarParaEliminar(v,c,request);
		c.eliminarProductoEnPedido(request.getIdVariante(),request.getIdPedido(),request.getCantidad());
		v.eliminarReserva(request.getCantidad());
		usuarioDAO.guardarUsuario(c);
		productoService.modificarVariante(v);
	}

	private void validarParaEliminar(Variante v, Cliente c, AgregarQuitarProductoAPedidoRequest request) {
		validacionesGenerales(v, c, request);
		if(!c.contieneProductoEnPedido(v,request.getIdPedido())){
			throw new ProductoInexsistenteException("El usuario no tiene el producto con ID: "+ request.getIdVariante()+" En el pedido");
		}
		if(!c.contieneCantidadDeProductoEnPedido(v,request.getIdPedido(),request.getCantidad())){
			throw new ProductoInexsistenteException("No se puede quitar mas cantidad de un producto de la que el usuario posee en su pedido");
		}
		
		
	}

	@Override
	public void eliminarPedidoPara(String email, Integer idPedido) {
		Cliente c = usuarioDAO.obtenerClienteConPedido(email);
		validarEliminacionDePedidoPara(c,idPedido);
		Pedido p = c.eliminarPedido(idPedido);
		productoService.eliminarReservasDe(p);
		usuarioDAO.guardarUsuario(c);
	}

	private void validarEliminacionDePedidoPara(Cliente c, Integer idPedido) {
		if(!c.contienePedidoVigente(idPedido)){
			throw new PedidoVigenteException("El usuario no posee el pedido con ID:" + idPedido+" o el mismo no se encuentra vigente");
		}
		
	}

	@Override
	public void eliminarUsuario(Vendedor u) {
		usuarioDAO.eliminarUsuario(u);
		
	}


	

	

}
