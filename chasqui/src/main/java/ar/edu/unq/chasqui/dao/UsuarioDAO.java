package ar.edu.unq.chasqui.dao;

import java.util.List;

import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Notificacion;
import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.model.Vendedor;

public interface UsuarioDAO {

	
	public Usuario obtenerUsuarioPorID(Integer id);
	public Vendedor obtenerVendedorPorID(Integer id);
	public void guardarUsuario(Usuario u);
	public Usuario obtenerUsuarioPorNombre (String username);
	public void merge(Vendedor usuario);
	public Usuario obtenerUsuarioPorEmail(String email);
	public boolean existeUsuarioCon(String email);
	public void inicializarListasDe(Vendedor usuarioLogueado);
	public Cliente obtenerClienteConDireccionPorEmail(final String email);
	public Cliente obtenerClienteConPedido(String mail);
	public void eliminarUsuario(Vendedor u);
	public List<Notificacion> obtenerNotificacionesDe(String mail, Integer pagina);
	public Cliente obtenerClienteConPedidoEHistorial(String mail);
}
