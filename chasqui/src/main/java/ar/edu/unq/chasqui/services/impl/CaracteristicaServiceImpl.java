package ar.edu.unq.chasqui.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.dao.CaracteristicaDAO;
import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.CaracteristicaProductor;
import ar.edu.unq.chasqui.services.interfaces.CaracteristicaService;

public class CaracteristicaServiceImpl implements CaracteristicaService{

	@Autowired
	private CaracteristicaDAO caracteristicaDAO;
	
	
	
	
	public void guardaCaracteristicasProducto(List<Caracteristica> list) {
		caracteristicaDAO.guardaCaracteristicasProducto(list);
		
	}

	public void guardarCaracteristicaProductor(List<CaracteristicaProductor> list) {
		caracteristicaDAO.guardarCaracteristicaProductor(list);
		
	}

	public List<Caracteristica> buscarCaracteristicasProductoBy(Integer idVendedor) {
		return caracteristicaDAO.buscarCaracteristicasProductoBy(idVendedor);
	}

	public List<CaracteristicaProductor> buscarCaracteristicasProductorBy(Integer idVendedor) {
		return caracteristicaDAO.buscarCaracteristicasProductorBy(idVendedor);
	}

	
	
	public CaracteristicaDAO getCaracteristicaDAO() {
		return caracteristicaDAO;
	}

	public void setCaracteristicaDAO(CaracteristicaDAO caracteristicaDAO) {
		this.caracteristicaDAO = caracteristicaDAO;
	}

	
	
	
	
	
	
	
}
