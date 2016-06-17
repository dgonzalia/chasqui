package ar.edu.unq.chasqui.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unq.chasqui.dao.VendedorDAO;
import ar.edu.unq.chasqui.model.Vendedor;

@SuppressWarnings("unchecked")
public class VendedorDAOHbm  extends HibernateDaoSupport implements VendedorDAO{

	
	@Override
	public List<Vendedor> obtenerVendedores() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Vendedor.class);
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

}
