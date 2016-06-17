package ar.edu.unq.chasqui.services.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Categoria;

public interface CategoriaService {

	
	@Transactional
	public List<Categoria> obtenerCategoriasDe(Integer idVendedor);



}
