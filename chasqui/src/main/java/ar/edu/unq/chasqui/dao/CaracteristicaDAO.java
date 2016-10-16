package ar.edu.unq.chasqui.dao;

import java.util.List;

import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.CaracteristicaProductor;

public interface CaracteristicaDAO {

	
	public void guardaCaracteristicasProducto(List<Caracteristica>list);	
	public void guardarCaracteristicaProductor(List<CaracteristicaProductor>list);	
	public List<Caracteristica> buscarCaracteristicasProducto();
	public List<CaracteristicaProductor> buscarCaracteristicasProductor();
	public void eliminarCaracteristica(Caracteristica c);
	public void eliminarCaracteristicaProductor(CaracteristicaProductor c);
	public void actualizarCaracteristicaProductor(CaracteristicaProductor c);
	public void actualizar(Caracteristica c);
	
}
