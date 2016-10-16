package ar.edu.unq.chasqui.services.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Fabricante;

public interface ProductorService {

	
	@Transactional
	public List<Fabricante>obtenerProductoresDe(Integer idVendedor);
//	@Transactional
//	public void eliminarProductor(Fabricante f);
}
