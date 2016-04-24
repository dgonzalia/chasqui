package ar.edu.unq.chasqui.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.dao.ZonaDAO;
import ar.edu.unq.chasqui.model.Zona;
import ar.edu.unq.chasqui.services.interfaces.ZonaService;

public class ZonaServiceImpl implements ZonaService{

	
	@Autowired
	private ZonaDAO zonaDAO;
	
	public void guardar(Zona z) {
		zonaDAO.guardar(z);
		
	}

	public void borrar(Zona z) {
		zonaDAO.eliminar(z);		
	}

	
	
	public List<Zona> buscarZonasBy(Integer idUsuario) {
		return zonaDAO.buscarZonasBy(idUsuario);
	}
	
	
	
	
	
	public ZonaDAO getZonaDAO() {
		return zonaDAO;
	}

	public void setZonaDAO(ZonaDAO zonaDAO) {
		this.zonaDAO = zonaDAO;
	}

	
	
	
	

	
	
}
