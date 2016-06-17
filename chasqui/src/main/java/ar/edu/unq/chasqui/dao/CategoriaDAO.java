package ar.edu.unq.chasqui.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Categoria;

public interface CategoriaDAO {
	
	@Transactional
	public List<Categoria> obtenerCategoriasDe(Integer idVendedor);

}
