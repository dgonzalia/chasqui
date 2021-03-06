package ar.edu.unq.chasqui.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unq.chasqui.dao.ProductoDAO;
import ar.edu.unq.chasqui.exceptions.ProductoInexsistenteException;
import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Variante;

@SuppressWarnings("unchecked")
public class ProductoDAOHbm extends HibernateDaoSupport implements ProductoDAO{

	@Override
	public List<Variante> obtenerVariantesPorCategoria(final Integer idCategoria, final Integer pagina,
			final Integer cantidadDeItems) {
		
		final Integer inicio = calcularInicio(pagina,cantidadDeItems);
		final Integer fin = calcularFin(pagina,cantidadDeItems);
		return this.getHibernateTemplate().execute(new HibernateCallback<List<Variante>>() {

			@Override
			public List<Variante> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Variante.class);
				c.createAlias("producto", "p")
				 .createAlias("p.categoria", "c")
				 .add(Restrictions.eq("c.id", idCategoria))
				 .add(Restrictions.sqlRestriction("( STOCK - RESERVADOS) > 0"))
				 .addOrder(Order.asc("id"))
				 .setFirstResult(inicio )
				 .setMaxResults(cantidadDeItems);
				return (List<Variante>)c.list();
			}
		});
	}

	@Override
	public List<Variante> obtenerVariantesPorProductor(final Integer idProductor, final Integer pagina, final Integer cantItems) {
		final Integer inicio = calcularInicio(pagina,cantItems);
		final Integer fin = calcularFin(pagina,cantItems);
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Variante>>() {

			@Override
			public List<Variante> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Variante.class);
				c.createAlias("producto", "p")
				 .createAlias("p.fabricante", "f")
				 .add(Restrictions.eq("f.id", idProductor))
				 .add(Restrictions.sqlRestriction("( STOCK - RESERVADOS) > 0"))
				 .setFirstResult(inicio)
				 .setMaxResults(cantItems)
				 .addOrder(Order.asc("id"));
				return (List<Variante>)c.list();
			}
		});
	}
	
	
	@Override
	public List<Variante> obtenerVariantesPorMedalla(final Integer idMedalla, final Integer pagina, final Integer cantItems,final Integer idVendedor) {
		final Integer inicio = calcularInicio(pagina,cantItems);
		final Integer fin = calcularFin(pagina,cantItems);
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Variante>>() {

			@Override
			public List<Variante> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Variante.class);
				c.createAlias("producto", "p")
				 .createAlias("p.caracteristicas", "m")
				 .createAlias("p.fabricante", "f")
				 .add(Restrictions.eq("m.id", idMedalla))
				 .add(Restrictions.eq("f.idVendedor",idVendedor))
				 .add(Restrictions.sqlRestriction("( STOCK - RESERVADOS) > 0"))
				 .setFirstResult(inicio)
				 .setMaxResults(cantItems)
				 .addOrder(Order.asc("id"));
				return  (List<Variante>)c.list();
			}
		});
	}
	
	

	@Override
	public List<Variante> obtenerVariantesSinFiltro(Integer pagina, final Integer cantItems, final Integer idVendedor) {
		final Integer inicio = calcularInicio(pagina,cantItems);
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Variante>>() {

			@Override
			public List<Variante> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Variante.class);
				c.createAlias("producto", "p")
				 .createAlias("p.fabricante", "f")
				 .add(Restrictions.eq("f.idVendedor",idVendedor))
				 .add(Restrictions.sqlRestriction("( STOCK - RESERVADOS) > 0"))
				 .setFirstResult(inicio)
				 .setMaxResults(cantItems)
				 .addOrder(Order.asc("id"));
				return  (List<Variante>)c.list();
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
		return (pagina - 1) * cantidadDeItems;
	}

	@Override
	public List<Variante> obtenerVariantesPorNombreODescripcion(final String param,Integer pagina,final Integer cantItems,final Integer idVendedor) {
		final Integer inicio = calcularInicio(pagina,cantItems);
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Variante>>() {

			@Override
			public List<Variante> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Variante.class);
				Disjunction or = Restrictions.disjunction();
				or.add(Restrictions.like("nombre","%" +param+ "%" ));
				or.add(Restrictions.like("p.nombre","%" +param+ "%"));
				or.add(Restrictions.like("descripcion","%" +param+ "%"));
				c.createAlias("producto", "p")
				 .createAlias("p.categoria", "c")
				 .createAlias("c.vendedor", "v")
				 .add(Restrictions.eq("v.id", idVendedor))
				 .add(Restrictions.sqlRestriction("( STOCK - RESERVADOS) > 0"))
				 .add(or)
				 .setFirstResult(inicio)
				 .setMaxResults(cantItems)
				 .addOrder(Order.asc("id"));
				return  (List<Variante>)c.list();
			}
		});
	}

	@Override
	public Variante obtenervariantePor(final Integer id) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Variante>() {

			@Override
			public Variante doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Variante.class);
				criteria.add(Restrictions.eq("id", id));
				return (Variante) criteria.uniqueResult();
			}
		});
	}

	@Override
	public void modificarVariante(Variante v) {
		this.getHibernateTemplate().saveOrUpdate(v);
		this.getHibernateTemplate().flush();		
	}

	@Override
	public Caracteristica obtenerCaracteristicaPor(final Integer idMedalla) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Caracteristica>() {

			@Override
			public Caracteristica doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Caracteristica.class);
				criteria.add(Restrictions.eq("id", idMedalla));
				return (Caracteristica) criteria.uniqueResult();
			}
		});
		
	}

	@Override
	public Long totalVariantesPorCategoria(final Integer idCategoria) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Long>() {

			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Variante.class);
				c.createAlias("producto", "p")
				 .createAlias("p.categoria", "c")
				 .add(Restrictions.eq("c.id", idCategoria))
				 .setProjection(Projections.rowCount());				
				return  (Long)c.uniqueResult();
			}
		});
	}

	@Override
	public Long totalVariantesPorProductor(final Integer idProductor) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Long>() {

			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Variante.class);
				c.createAlias("producto", "p")
				 .createAlias("p.fabricante", "f")
				 .add(Restrictions.eq("f.id", idProductor))
				 .setProjection(Projections.rowCount());				
				return  (Long)c.uniqueResult();
			}
		});
	}

	@Override
	public Long totalVariantesPorMedalla(final Integer idMedalla) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Long>() {

			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Variante.class);
				c.createAlias("producto", "p")
				 .createAlias("p.caracteristicas", "m")
				 .add(Restrictions.eq("m.id", idMedalla))
				 .setProjection(Projections.rowCount());
				
				return  (Long)c.uniqueResult();
			}
		});
	}

	@Override
	public Long totalVariantesPorNombreODescripcion(final String param,final Integer idVendedor) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Long>() {

			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Variante.class);
				Disjunction or = Restrictions.disjunction();
				or.add(Restrictions.like("nombre","%" +param+ "%" ));
				or.add(Restrictions.like("p.nombre","%" +param+ "%"));
				or.add(Restrictions.like("descripcion","%" +param+ "%"));
				c.createAlias("producto", "p")
				 .createAlias("p.categoria", "c")
				 .createAlias("c.vendedor", "v")
				 .add(Restrictions.eq("v.id", idVendedor))
				 .add(or)
				 .setProjection(Projections.rowCount());
				return  (Long) c.uniqueResult();
			}
		});
	}

	@Override
	public Long totalVariantesSinFiltro(final Integer idVendedor) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Long>() {

			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Variante.class);
				c.createAlias("producto", "p")
				 .createAlias("p.fabricante", "f")
				 .add(Restrictions.eq("f.idVendedor", idVendedor))
				 .setProjection(Projections.rowCount());				
				return  (Long)c.uniqueResult();
			}
		});
	}
	
	
	

}
