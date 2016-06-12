package ar.edu.unq.chasqui.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unq.chasqui.dao.UsuarioDAO;
import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.model.Vendedor;

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

	public Vendedor obtenerVendedorPorID(final Integer id) {
		Vendedor v = this.getHibernateTemplate().execute(new HibernateCallback<Vendedor>() {

			public Vendedor doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Vendedor.class);
				criteria.add(Restrictions.eq("id", id));
				return (Vendedor) criteria.uniqueResult();
			}
			
		});
		return v;		
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

	public void merge(Vendedor usuario) {
		this.getHibernateTemplate().merge(usuario);
		this.getHibernateTemplate().flush();
		
	}

	public Usuario obtenerUsuarioPorEmail(String email) {
		DetachedCriteria d = DetachedCriteria.forClass(Usuario.class);
		d.add(Restrictions.eq("email",email));
		List<Usuario> u = (List<Usuario>) this.getHibernateTemplate().findByCriteria(d);
		if(u == null || u.size() > 1){
			return null;
		}
		return u.get(0);
	}
	
	
	public boolean existeUsuarioCon(String email) {
		DetachedCriteria d = DetachedCriteria.forClass(Usuario.class);
		d.add(Restrictions.eq("email",email));
		List<Usuario> u = (List<Usuario>) this.getHibernateTemplate().findByCriteria(d);
		return (u == null || u.size() > 0);
	}

	

}
