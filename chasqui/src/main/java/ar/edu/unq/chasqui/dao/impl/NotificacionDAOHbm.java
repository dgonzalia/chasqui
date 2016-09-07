package ar.edu.unq.chasqui.dao.impl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unq.chasqui.dao.NotificacionDAO;
import ar.edu.unq.chasqui.model.Notificacion;

public class NotificacionDAOHbm  extends HibernateDaoSupport implements NotificacionDAO{

	@Override
	public void guardar(Notificacion n) {
		this.getSession().saveOrUpdate(n);
		this.getSession().flush();
		
	}

}
