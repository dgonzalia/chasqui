package ar.edu.unq.chasqui.services.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.CaracteristicaProductor;

public interface CaracteristicaService {

	@Transactional
	public void guardaCaracteristicasProducto(List<Caracteristica>list);
	@Transactional
	public void guardarCaracteristicaProductor(List<CaracteristicaProductor>list);
	@Transactional
	public void eliminarCaracteristica(Caracteristica c);
	@Transactional
	public void eliminarCaracteristicaProductor(CaracteristicaProductor c);
	@Transactional
	public List<Caracteristica> buscarCaracteristicasProducto();
	@Transactional
	public List<CaracteristicaProductor> buscarCaracteristicasProductor();
	
}
