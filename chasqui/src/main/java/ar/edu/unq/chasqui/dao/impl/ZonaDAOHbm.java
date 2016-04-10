package ar.edu.unq.chasqui.dao.impl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unq.chasqui.dao.ZonaDAO;
import ar.edu.unq.chasqui.model.Zona;

public class ZonaDAOHbm extends HibernateDaoSupport implements ZonaDAO{

	
	
	public void guardar(Zona z) {
		this.getHibernateTemplate().saveOrUpdate(z);
		
	}

	public void eliminar(Zona z) {
	this.getHibernateTemplate().delete(z);
		
	}

	
	
	
}
