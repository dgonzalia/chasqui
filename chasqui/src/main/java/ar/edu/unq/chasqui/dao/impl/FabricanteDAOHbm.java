package ar.edu.unq.chasqui.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unq.chasqui.dao.FabricanteDAO;
import ar.edu.unq.chasqui.exceptions.VendedorInexistenteException;
import ar.edu.unq.chasqui.model.Fabricante;

@SuppressWarnings("unchecked")
public class FabricanteDAOHbm extends HibernateDaoSupport implements FabricanteDAO{

	@Override
	public List<Fabricante> obtenerProductoresDe(Integer idVendedor) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<Fabricante>>() {

			@Override
			public List<Fabricante> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "SELECT * FROM PRODUCTOR WHERE ID_VENDEDOR = :vendedor";
				Query hql = session.createSQLQuery(sql);
				List<Fabricante> resultado = (List<Fabricante>) hql.list();
				
				if(resultado == null || resultado.size() == 0 ){
					throw new VendedorInexistenteException();
				}
				return resultado; 
			}
		});
	}
	
	

}
