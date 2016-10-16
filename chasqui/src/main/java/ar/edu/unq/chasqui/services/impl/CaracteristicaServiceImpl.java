package ar.edu.unq.chasqui.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.aspect.Auditada;
import ar.edu.unq.chasqui.dao.CaracteristicaDAO;
import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.CaracteristicaProductor;
import ar.edu.unq.chasqui.services.interfaces.CaracteristicaService;

@Auditada
public class CaracteristicaServiceImpl implements CaracteristicaService{

	@Autowired
	private CaracteristicaDAO caracteristicaDAO;
	
	
	
	
	public void guardaCaracteristicasProducto(List<Caracteristica> list) {
		caracteristicaDAO.guardaCaracteristicasProducto(list);
		
	}

	public void guardarCaracteristicaProductor(List<CaracteristicaProductor> list) {
		caracteristicaDAO.guardarCaracteristicaProductor(list);
		
	}

	public List<Caracteristica> buscarCaracteristicasProducto() {
		return caracteristicaDAO.buscarCaracteristicasProducto();
	}

	public List<CaracteristicaProductor> buscarCaracteristicasProductor() {
		return caracteristicaDAO.buscarCaracteristicasProductor();
	}

	public void eliminarCaracteristica(Caracteristica c){
		caracteristicaDAO.eliminarCaracteristica(c);
	}
	
	public void eliminarCaracteristicaProductor(CaracteristicaProductor c){
		caracteristicaDAO.eliminarCaracteristicaProductor(c);
	}
	
	
	public CaracteristicaDAO getCaracteristicaDAO() {
		return caracteristicaDAO;
	}

	public void setCaracteristicaDAO(CaracteristicaDAO caracteristicaDAO) {
		this.caracteristicaDAO = caracteristicaDAO;
	}

	@Override
	public void actualizarCaracteristica(Caracteristica c) {
		caracteristicaDAO.actualizar(c);
		
	}
	
	
	@Override
	public void actualizarCaracteristicaProductor(CaracteristicaProductor c) {
		caracteristicaDAO.actualizarCaracteristicaProductor(c);
		
	}

	
	
	
	
	
	
	
}
