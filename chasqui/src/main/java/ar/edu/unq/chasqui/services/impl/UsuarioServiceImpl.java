package ar.edu.unq.chasqui.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.dao.UsuarioDAO;
import ar.edu.unq.chasqui.exceptions.DireccionesInexistentes;
import ar.edu.unq.chasqui.exceptions.UsuarioExistenteException;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Direccion;
import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.security.Encrypter;
import ar.edu.unq.chasqui.security.PasswordGenerator;
import ar.edu.unq.chasqui.service.rest.request.DireccionRequest;
import ar.edu.unq.chasqui.service.rest.request.EditarPerfilRequest;
import ar.edu.unq.chasqui.service.rest.request.SingUpRequest;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioDAO usuarioDAO;
	
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
			user.setPassword(Encrypter.encrypt("chasquiadmin"));
			user.setEmail("jfflores90@gmail");
			user.setIsRoot(true);
			Imagen img = new Imagen();
			img.setNombre("perfil.jpg");
			img.setPath("/imagenes/usuarios/"+user.getUsername()+"/perfil.jpg");
			user.setImagenPerfil(img.getPath());
			usuarioDAO.guardarUsuario(user);	
			
			Vendedor u2 = new Vendedor();
			u2.setUsername("MatLock");
			u2.setPassword(Encrypter.encrypt("federico"));
			u2.setEmail("jfflores90@gmail.com");
			u2.setIsRoot(false);
			u2.setImagenPerfil(img.getPath());
			usuarioDAO.guardarUsuario(u2);	
			
		
			Cliente c = new Cliente();
			c.setToken("federico");
			c.setPassword(Encrypter.encrypt("federico"));
			c.setEmail("mat90@gmail.com");
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
			String passwordUser = Encrypter.decrypt(usuario.getPassword());
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
			String passwordUser = Encrypter.decrypt(c.getPassword());
			if(passwordUser.equals(password)){
				String token = PasswordGenerator.generateRandomToken();
				c.setToken(token);
				usuarioDAO.guardarUsuario(c);
				return c;				
			}
		}
		throw new UsuarioExistenteException("No existe el usuario");
	}

	public Cliente crearCliente(SingUpRequest request) throws Exception {
		Cliente c = new Cliente(request);
		usuarioDAO.guardarUsuario(c);
		return c;
	}

	@Override
	public void modificarUsuario(EditarPerfilRequest editRequest,String email) throws Exception {
		Cliente c = (Cliente) usuarioDAO.obtenerUsuarioPorEmail(email);
		if(c == null){
			throw new UsuarioExistenteException("No existe el usuario");
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


	

	

}
