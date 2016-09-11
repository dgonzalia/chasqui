package ar.edu.unq.chasqui.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.aspect.Auditada;
import ar.edu.unq.chasqui.dao.FabricanteDAO;
import ar.edu.unq.chasqui.model.Fabricante;
import ar.edu.unq.chasqui.services.interfaces.ProductorService;

@Auditada
public class FabricanteServiceImpl implements ProductorService{

	@Autowired
	FabricanteDAO fabricanteDAO;
	
	
	@Override
	public List<Fabricante> obtenerProductoresDe(Integer idVendedor) {
		return fabricanteDAO.obtenerProductoresDe(idVendedor);
	}

}
