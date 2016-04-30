package ar.edu.unq.chasqui.services.interfaces;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.model.Vendedor;

public interface UsuarioService {

	
	public Usuario obtenerUsuarioPorID(final Integer id);
	public Usuario obtenerVendedorPorID(final Integer id);
	public Usuario login (final String username, final String password) throws Exception;
	@Transactional
	public void modificarPasswordUsuario(String usuario,String password);
	@Transactional
	public void guardarUsuario(Usuario u);
	@Transactional
	public void merguear(Vendedor usuario);
	public Usuario obtenerUsuarioPorEmail(String email);
}
