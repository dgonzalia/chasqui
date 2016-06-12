package ar.edu.unq.chasqui.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.dao.UsuarioDAO;
import ar.edu.unq.chasqui.exceptions.UsuarioExistenteException;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.security.Encrypter;
import ar.edu.unq.chasqui.security.PasswordGenerator;
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
	
	public void modificarPasswordUsuario(String usuario,String password){
		Usuario u = usuarioDAO.obtenerUsuarioPorNombre(usuario);
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
			u2.setEmail("jfflores90");
			u2.setIsRoot(false);
			u2.setImagenPerfil(img.getPath());
			usuarioDAO.guardarUsuario(u2);	
			
		
			Cliente c = new Cliente();
			c.setToken("federico");
			c.setPassword(Encrypter.encrypt("federico"));
			c.setEmail("jfflores90@gmail.com");
			c.setNombre("JORGE");
			c.setNickName("MatLock");
			
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
	public void modificarUsuario(EditarPerfilRequest editRequest) throws Exception {
		Cliente c = (Cliente) usuarioDAO.obtenerUsuarioPorEmail(editRequest.getEmail());
		if(c == null){
			throw new UsuarioExistenteException("No existe el usuario");
		}
		c.modificarCon(editRequest);
		usuarioDAO.guardarUsuario(c);
	}


	

	

}
