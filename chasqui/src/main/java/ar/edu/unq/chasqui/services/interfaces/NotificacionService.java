package ar.edu.unq.chasqui.services.interfaces;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Notificacion;

public interface NotificacionService {

	@Transactional
	public void guardar(Notificacion n,String idDispositivo);
	
}
