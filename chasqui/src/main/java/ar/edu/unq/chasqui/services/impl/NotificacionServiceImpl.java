package ar.edu.unq.chasqui.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.aspect.Auditada;
import ar.edu.unq.chasqui.dao.NotificacionDAO;
import ar.edu.unq.chasqui.model.Notificacion;
import ar.edu.unq.chasqui.services.interfaces.NotificacionService;


@Auditada
public class NotificacionServiceImpl implements NotificacionService{

	@Autowired
	NotificacionDAO notificacionDAO;
	
	@Override
	public void guardar(Notificacion n) {
		notificacionDAO.guardar(n);
		
	}

}
