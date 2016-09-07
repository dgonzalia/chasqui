package ar.edu.unq.chasqui.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unq.chasqui.dao.PedidoDAO;
import ar.edu.unq.chasqui.model.Pedido;
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

	@Override
	public void guardar(Pedido p) {
		this.getHibernateTemplate().saveOrUpdate(p);
		this.getHibernateTemplate().flush();
		
	}

}
