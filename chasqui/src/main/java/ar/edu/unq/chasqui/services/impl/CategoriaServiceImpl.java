package ar.edu.unq.chasqui.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.aspect.Auditada;
import ar.edu.unq.chasqui.dao.CategoriaDAO;
import ar.edu.unq.chasqui.model.Categoria;
import ar.edu.unq.chasqui.services.interfaces.CategoriaService;

@Auditada
public class CategoriaServiceImpl implements CategoriaService {

	
	@Autowired
	private CategoriaDAO categoriaDAO;

	@Override
	public List<Categoria> obtenerCategoriasDe(Integer idVendedor) {
		return categoriaDAO.obtenerCategoriasDe(idVendedor);
	}
	
	
	
	
	
}
