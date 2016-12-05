package ar.edu.unq.chasqui.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unq.chasqui.dao.UsuarioDAO;
import ar.edu.unq.chasqui.model.Categoria;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Fabricante;
import ar.edu.unq.chasqui.model.Notificacion;
import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.model.Vendedor;

@SuppressWarnings("unchecked")
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
	
	public void inicializarListasDe(Vendedor v) {
		HibernateTemplate ht =this.getHibernateTemplate();
		ht.refresh(v);
		ht.initialize(v.getFabricantes());
		ht.initialize(v.getCategorias());
		for(Categoria c : v.getCategorias()){
			ht.initialize(c.getProductos());
			for(Producto p : c.getProductos()){
				ht.initialize(p.getVariantes());
			}
		}
		for(Fabricante f : v.getFabricantes()){
			ht.initialize(f.getProductos());
		}
		
		
		
	}

	
	public void merge(Vendedor usuario) {
		this.getHibernateTemplate().merge(usuario);
		this.getHibernateTemplate().flush();
		
	}

	public Usuario obtenerUsuarioPorEmail(final String email) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Usuario>() {

			@Override
			public Usuario doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria d = session.createCriteria(Usuario.class);
				d.add(Restrictions.eq("email",email));
				return (Usuario) d.uniqueResult();
			}
		});
	}
	
	public Cliente obtenerClienteConDireccionPorEmail(final String email){
		Cliente c = (Cliente) this.obtenerUsuarioPorEmail(email);
		if(c != null){
			Hibernate.initialize(c.getDireccionesAlternativas());			
		}
		return c;
	}
	
	
	public boolean existeUsuarioCon(String email) {
		DetachedCriteria d = DetachedCriteria.forClass(Usuario.class);
		d.add(Restrictions.eq("email",email));
		List<Usuario> u = (List<Usuario>) this.getHibernateTemplate().findByCriteria(d);
		return (u == null || !u.isEmpty());
	}

	@Override
	public Cliente obtenerClienteConPedido(String mail) {
		Cliente c = (Cliente) this.obtenerUsuarioPorEmail(mail);
		Hibernate.initialize(c.getPedidos());
		return c;
	}
	
	@Override
	public Cliente obtenerClienteConPedidoEHistorial(String mail) {
		Cliente c = (Cliente) this.obtenerUsuarioPorEmail(mail);
		Hibernate.initialize(c.getPedidos());
		if(c.getHistorialPedidos() != null){
			Hibernate.initialize(c.getHistorialPedidos().getPedidos());			
		}
		return c;
	}

	@Override
	public void eliminarUsuario(Vendedor u) {
		this.getHibernateTemplate().delete(u);
		this.getHibernateTemplate().flush();
	}

	@Override
	public List<Notificacion> obtenerNotificacionesDe(final String mail, final Integer pagina) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Notificacion>>() {

			@Override
			public List<Notificacion> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Notificacion.class);
				Integer inicio = calcularInicio(pagina, 5);
				criteria.setFirstResult(inicio)
						.setMaxResults(5)
						.add(Restrictions.eq("usuarioDestino", mail))
						.addOrder(Order.desc("id"));
				return criteria.list();
			}			
		});
	}

	
	
	private Integer calcularInicio(Integer pagina, Integer cantidadDeItems) {		
		if(pagina == 1){
			return 0;
		}
		return (pagina - 1) * cantidadDeItems;
	}

	@Override
	public List<Notificacion> obtenerNotificacionNoLeidas(final String mail) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Notificacion>>() {

			@Override
			public List<Notificacion> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Notificacion.class);
				criteria.add(Restrictions.eq("usuarioDestino", mail))
						.add(Restrictions.eq("estado","No Leído"))
						.addOrder(Order.desc("id"));
				return criteria.list();
			}			
		});
	}

	@Override
	public Integer obtenerTotalNotificacionesDe(final String mail) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Notificacion.class);
				criteria.add(Restrictions.eq("usuarioDestino", mail))
						.addOrder(Order.desc("id"))
						.setProjection(Projections.rowCount());				
				return  ((Long)criteria.uniqueResult()).intValue();
			}			
		});
	}

	
	@Override
	public Notificacion obtenerNotificacion(final Integer id) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Notificacion>() {

			@Override
			public Notificacion doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Notificacion.class);
				criteria.add(Restrictions.eq("id", id));			
				return  (Notificacion) criteria.uniqueResult();
			}			
		});
	}

	@Override
	public void guardar(Notificacion n) {
		this.getHibernateTemplate().saveOrUpdate(n);
		this.getHibernateTemplate().flush();
		
	}

	/**
	 * ESTE METODO SOLO DEBE USARSE PARA LOS TEST
	 */
	@Override
	public <T> void deleteObject(T obj) {
		this.getHibernateTemplate().execute(new HibernateCallback<T>() {

			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				Query deleteProductoPedido = session.createSQLQuery("DELETE FROM PRODUCTO_PEDIDO");
				Query deletePedido = session.createSQLQuery("DELETE FROM PEDIDO");
				deleteProductoPedido.executeUpdate();
				deletePedido.executeUpdate();
				return null;
			}
		});
		this.getHibernateTemplate().delete(obj);
		this.getHibernateTemplate().flush();
		this.getHibernateTemplate().clear();
		
	}
	
	
	

}
