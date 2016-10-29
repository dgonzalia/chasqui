package ar.edu.unq.chasqui.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unq.chasqui.dao.PedidoDAO;
import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.model.Variante;
import ar.edu.unq.chasqui.view.composer.Constantes;

public class PedidoDAOHbm extends HibernateDaoSupport implements PedidoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Pedido> obtenerPedidosProximosAVencer(final Integer cantidadDeDias,final Integer idVendedor,final DateTime fechaCierrePedido) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<Pedido>>() {

			@Override
			public List<Pedido> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Pedido.class);
				criteria.add(Restrictions.eq("alterable",true))
						.add(Restrictions.eq("idVendedor", idVendedor))
						.add(Restrictions.eq("fechaDeVencimiento", fechaCierrePedido))
						.add(Restrictions.eq("estado", Constantes.ESTADO_PEDIDO_ABIERTO))
						.add(Restrictions.eq("perteneceAPedidoGrupal", false))
						.add(Restrictions.le("fechaDeVencimiento", new DateTime().plusDays(cantidadDeDias)));
				return (List<Pedido>) criteria.list();
			}
		});
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Pedido> obtenerPedidos(final Integer idVendedor) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Pedido>>() {

			@Override
			public List<Pedido> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Pedido.class)
				 .add(Restrictions.eq("idVendedor", idVendedor))
				 .setMaxResults(100)
				 .addOrder(Order.desc("id"));
				return (List<Pedido>)c.list();
			}
		});
	}


	@Override
	public void guardar(Pedido p) {
		this.getHibernateTemplate().saveOrUpdate(p);
		this.getHibernateTemplate().flush();
		
	}
	

	@Override
	public Integer obtenerTotalPaginasDePedidosParaVendedor(final Integer id) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Pedido.class);
				c.add(Restrictions.eq("idVendedor", id))
				 .setProjection(Projections.rowCount());				
				 return ((Long)c.uniqueResult()).intValue();
			}
		});
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Pedido> obtenerPedidos(final Integer idVendedor,final Date desde,final  Date hasta, final String estadoSeleccionado) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Pedido>>() {

			@Override
			public List<Pedido> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(Pedido.class)
				 .add(Restrictions.eq("idVendedor", idVendedor))
				 .setMaxResults(100)
				 .addOrder(Order.desc("id"));
				if(!StringUtils.isEmpty(estadoSeleccionado)){
					c.add(Restrictions.eq("estado", estadoSeleccionado));
				}
				if(desde != null && hasta != null){
					DateTime d = new DateTime(desde.getTime());
					DateTime h = new DateTime(hasta.getTime());
					c.add(Restrictions.eq("fechaCreacion", d))
					.add(Restrictions.eq("fechaCreacion", h));
				}
				
				return (List<Pedido>)c.list();
			}
		});
	}

}
