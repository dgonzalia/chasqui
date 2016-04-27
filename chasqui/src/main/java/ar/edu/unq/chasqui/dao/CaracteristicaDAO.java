package ar.edu.unq.chasqui.dao;

import java.util.List;

import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.CaracteristicaProductor;

public interface CaracteristicaDAO {

	
	public void guardaCaracteristicasProducto(List<Caracteristica>list);	
	public void guardarCaracteristicaProductor(List<CaracteristicaProductor>list);	
	public List<Caracteristica> buscarCaracteristicasProductoBy(Integer idVendedor);
	public List<CaracteristicaProductor> buscarCaracteristicasProductorBy(Integer idVendedor);
	public void eliminarCaracteristica(Caracteristica c);
	public void eliminarCaracteristicaProductor(CaracteristicaProductor c);
	
}
