package ar.edu.unq.chasqui.dao;

import ar.edu.unq.chasqui.model.Usuario;

public interface UsuarioDAO {

	
	public Usuario obtenerUsuarioPorID(Integer id);
	public void guardarUsuario(Usuario u);
	public Usuario obtenerUsuarioPorNombre (String username);
}
