package ar.edu.unq.chasqui.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unq.chasqui.dao.CaracteristicaDAO;
import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.CaracteristicaProductor;

public class CaracteristicaDAOHbm extends HibernateDaoSupport implements CaracteristicaDAO{

	public void guardaCaracteristicasProducto(List<Caracteristica> list) {
		for(Caracteristica c : list){
			this.getHibernateTemplate().saveOrUpdate(c);
		}
		this.getHibernateTemplate().flush();
	}

	public void guardarCaracteristicaProductor(List<CaracteristicaProductor> list) {
		for(CaracteristicaProductor c : list){
			this.getHibernateTemplate().saveOrUpdate(c);
		}
		this.getHibernateTemplate().flush();
		
	}

	@SuppressWarnings("unchecked")
	public List<Caracteristica> buscarCaracteristicasProducto() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Caracteristica.class);		
		List<Caracteristica> resultado = this.getHibernateTemplate().findByCriteria(criteria);
		if(resultado == null){
			resultado = new ArrayList<Caracteristica>();
		}
		return resultado;
		
	}

	@SuppressWarnings("unchecked")
	public List<CaracteristicaProductor> buscarCaracteristicasProductor() {
		DetachedCriteria criteria = DetachedCriteria.forClass(CaracteristicaProductor.class);
		List<CaracteristicaProductor> resultado = this.getHibernateTemplate().findByCriteria(criteria);
		if(resultado == null){
			resultado = new ArrayList<CaracteristicaProductor>();
		}
		return resultado;
	}

	public void eliminarCaracteristica(Caracteristica c) {
		this.getHibernateTemplate().delete(c);
		this.getHibernateTemplate().flush();
		
	}

	public void eliminarCaracteristicaProductor(CaracteristicaProductor c) {
		this.getHibernateTemplate().delete(c);
		this.getHibernateTemplate().flush();
		
	}
	   
	
}
