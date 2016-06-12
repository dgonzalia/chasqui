package ar.edu.unq.chasqui.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unq.chasqui.dao.ProductoDAO;
import ar.edu.unq.chasqui.model.Producto;

public class ProductoDAOHbm extends HibernateDaoSupport implements ProductoDAO{

	@Override
	public List<Producto> obtenerProductos(final Integer idCategoria, final Integer pagina,
			final Integer cantidadDeItems) {
		
		final Integer inicio = calcularInicio(pagina,cantidadDeItems);
		final Integer fin = calcularFin(pagina,cantidadDeItems);
		return this.getHibernateTemplate().execute(new HibernateCallback<List<Producto>>() {

			@Override
			public List<Producto> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Producto.class);
				c.createAlias("categoria", "c");
				c.add(Restrictions.eq("c.id", idCategoria));
				c.add((Restrictions.between("id",inicio,fin)));
				c.addOrder(Order.desc("id"));
				return (List<Producto>)c.list();
			}
		});
	}

	private Integer calcularFin(Integer pagina, Integer cantidadDeItems) {
		return calcularInicio(pagina,cantidadDeItems) + cantidadDeItems;
	}

	private Integer calcularInicio(Integer pagina, Integer cantidadDeItems) {		
		if(pagina == 1){
			return 0;
		}
		return pagina * cantidadDeItems;
	}
	
	
	

}
