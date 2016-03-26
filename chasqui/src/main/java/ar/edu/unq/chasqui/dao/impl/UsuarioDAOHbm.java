package ar.edu.unq.chasqui.dao.impl;

import java.sql.SQLException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unq.chasqui.dao.UsuarioDAO;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Usuario;

public class UsuarioDAOHbm extends HibernateDaoSupport implements UsuarioDAO {

	public Usuario obtenerUsuarioPorID(final Integer id) {
		Usuario u = this.getHibernateTemplate().execute(new HibernateCallback<Usuario>() {

			public Usuario doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Usuario.class);
				criteria.add(Restrictions.eq("id", id));
				return (Usuario) criteria.uniqueResult();
			}
			
		});
		return u;		
	}

	public void guardarUsuario(Usuario u) {
		this.getHibernateTemplate().saveOrUpdate(u);
		this.getHibernateTemplate().flush();		
	}

	public Usuario obtenerUsuarioPorNombre(final String username) {
		Usuario u = this.getHibernateTemplate().execute(new HibernateCallback<Usuario>() {

			public Usuario doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Usuario.class);
				criteria.add(Restrictions.eq("username", username));
				return (Usuario) criteria.uniqueResult();
			}
			
		});
		return u;	
	}

}
