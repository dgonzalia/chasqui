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
	
	
	
	public List<Caracteristica> buscarCaracteristicasProductoBy(Integer idVendedor);
	public List<CaracteristicaProductor> buscarCaracteristicasProductorBy(Integer idVendedor);
	
}
