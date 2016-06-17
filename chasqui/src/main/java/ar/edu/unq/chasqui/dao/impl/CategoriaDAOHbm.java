package ar.edu.unq.chasqui.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unq.chasqui.dao.CategoriaDAO;
import ar.edu.unq.chasqui.exceptions.VendedorInexistenteException;
import ar.edu.unq.chasqui.model.Categoria;

@SuppressWarnings("unchecked")
public class CategoriaDAOHbm extends HibernateDaoSupport implements CategoriaDAO{

	@Override
	public List<Categoria> obtenerCategoriasDe(final Integer idVendedor) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<Categoria>>() {

			@Override
			public List<Categoria> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "SELECT * FROM CARACTERISTICA WHERE ID_VENDEDOR = :vendedor";
				Query hql = session.createSQLQuery(sql);
				List<Categoria> resultado = (List<Categoria>) hql.list();
				
				if(resultado == null || resultado.size() == 0 ){
					throw new VendedorInexistenteException();
				}
				return resultado; 
			}
		});
	}

}
