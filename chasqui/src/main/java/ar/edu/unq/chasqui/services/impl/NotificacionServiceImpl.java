package ar.edu.unq.chasqui.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import ar.edu.unq.chasqui.aspect.Auditada;
import ar.edu.unq.chasqui.dao.NotificacionDAO;
import ar.edu.unq.chasqui.model.Notificacion;
import ar.edu.unq.chasqui.services.interfaces.NotificacionService;


@Auditada
public class NotificacionServiceImpl implements NotificacionService{

	@Autowired
	NotificacionDAO notificacionDAO;
	@Autowired
	String GCM_API_KEY;
	
	@Override
	public void guardar(Notificacion n,String idDispositivo) {
		if(idDispositivo != null){
			enviarNotificacion(n,idDispositivo);
		}
		notificacionDAO.guardar(n);
	}
	
	private void enviarNotificacion(Notificacion n,String idDispositivo){
		Sender sender = new Sender(GCM_API_KEY);
		Message m = new Message.Builder().addData("message",n.getMensaje()).build();
		try{
			Result r = sender.send(m, idDispositivo, 2);
		}catch(Exception e){
			
		}
	}

}
