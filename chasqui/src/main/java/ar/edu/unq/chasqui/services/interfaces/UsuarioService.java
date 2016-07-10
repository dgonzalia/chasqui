package ar.edu.unq.chasqui.services.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.exceptions.DireccionesInexistentes;
import ar.edu.unq.chasqui.exceptions.UsuarioExistenteException;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Direccion;
import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.service.rest.request.DireccionRequest;
import ar.edu.unq.chasqui.service.rest.request.EditarPerfilRequest;
import ar.edu.unq.chasqui.service.rest.request.SingUpRequest;

public interface UsuarioService {

	
	public Usuario obtenerUsuarioPorID(final Integer id);
	public Usuario obtenerVendedorPorID(final Integer id);
	public Usuario login (final String username, final String password) throws Exception;
	public Cliente loginCliente(final String email, final String password) throws Exception;
	public boolean existeUsuarioCon(String email);
	@Transactional
	public void modificarPasswordUsuario(String usuario,String password);
	@Transactional
	public void guardarUsuario(Usuario u);
	@Transactional
	public void merguear(Vendedor usuario);
	public Usuario obtenerUsuarioPorEmail(String email);
	@Transactional
	public Cliente crearCliente(SingUpRequest request) throws Exception;
	@Transactional
	public void modificarUsuario(EditarPerfilRequest editRequest) throws Exception;
	@Transactional
	public List<Direccion> obtenerDireccionesDeUsuarioCon(String email) throws DireccionesInexistentes;
	@Transactional
	public void agregarDireccionAUsuarioCon(String mail, DireccionRequest request);
	@Transactional
	public void inicializarListasDe(Vendedor usuarioLogueado);
	@Transactional
	public void editarDireccionDe(String mail, DireccionRequest request, Integer idDireccion)throws DireccionesInexistentes,UsuarioExistenteException;
	@Transactional
	public void eliminarDireccionDe(String mail, Integer idDireccion)throws DireccionesInexistentes,UsuarioExistenteException;
	
}
