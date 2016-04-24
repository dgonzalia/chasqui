package ar.edu.unq.chasqui.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
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

	public List<Zona> buscarZonasBy(final Integer idUsuario) {
		List<Zona>zs = this.getHibernateTemplate().execute(new HibernateCallback<List<Zona>>() {

			@SuppressWarnings("unchecked")
			public List<Zona> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Zona.class);
				c.add(Restrictions.eq("idUsuario", idUsuario));
				return (List<Zona>) c.list();
			}
		});
		if(zs == null){
			return new ArrayList<Zona>();
		}
		return zs;
	}
	
	

	
	
	
}
