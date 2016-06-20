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

import ar.edu.unq.chasqui.dao.ProductoDAO;
import ar.edu.unq.chasqui.exceptions.ProductoInexsistenteException;
import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.model.Variante;

@SuppressWarnings("unchecked")
public class ProductoDAOHbm extends HibernateDaoSupport implements ProductoDAO{

	@Override
	public List<Producto> obtenerProductosPorCategoria(final Integer idCategoria, final Integer pagina,
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
				c.setMaxResults(cantidadDeItems);
				List<Producto>pss = (List<Producto>)c.list();
				return (pss == null  || pss.isEmpty() ? new ArrayList<Producto>():  pss);
			}
		});
	}

	@Override
	public List<Producto> obtenerProductosPorProductor(final Integer idProductor, final Integer pagina, final Integer cantItems) {
		final Integer inicio = calcularInicio(pagina,cantItems);
		final Integer fin = calcularFin(pagina,cantItems);
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Producto>>() {

			@Override
			public List<Producto> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Producto.class);
				c.createAlias("fabricante", "f");
				c.add(Restrictions.eq("f.id", idProductor));
				c.add(Restrictions.between("id", inicio,fin));
				c.setMaxResults(cantItems);
				List<Producto>pss = (List<Producto>)c.list();
				return (pss == null ? new ArrayList<Producto>():  pss);
			}
		});
	}
	
	
	@Override
	public List<Producto> obtenerProductosPorMedalla(final Integer idMedalla, final Integer pagina, final Integer cantItems) {
		final Integer inicio = calcularInicio(pagina,cantItems);
		final Integer fin = calcularFin(pagina,cantItems);
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Producto>>() {

			@Override
			public List<Producto> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Producto.class);
				c.createAlias("caracteristicas", "m");
				c.add(Restrictions.eq("m.id", idMedalla));
				c.add(Restrictions.between("id", inicio,fin));
				c.setMaxResults(cantItems);
				List<Producto>pss = (List<Producto>)c.list();
				return (pss == null ? new ArrayList<Producto>():  pss);
			}
		});
	}	

	@Override
	public List<Imagen> obtenerImagenesDe(final Integer idProducto) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<Imagen>>() {

			@Override
			public List<Imagen> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Variante.class);
				c.add(Restrictions.eq("id", idProducto));
				Variante v = (Variante) c.uniqueResult();
				if(v != null){
					return v.getImagenes();
				}
				throw new ProductoInexsistenteException();
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
