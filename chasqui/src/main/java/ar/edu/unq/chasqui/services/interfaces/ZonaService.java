package ar.edu.unq.chasqui.services.interfaces;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Zona;

public interface ZonaService {
	
	@Transactional
	public void guardar(Zona z);
	public void borrar(Zona z);

}
