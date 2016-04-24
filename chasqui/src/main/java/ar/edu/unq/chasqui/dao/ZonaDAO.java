package ar.edu.unq.chasqui.dao;

import java.util.List;

import ar.edu.unq.chasqui.model.Zona;

public interface ZonaDAO {
	
	public void guardar(Zona z);
	public void eliminar(Zona z);
	public List<Zona> buscarZonasBy(Integer idUsuario);

}
