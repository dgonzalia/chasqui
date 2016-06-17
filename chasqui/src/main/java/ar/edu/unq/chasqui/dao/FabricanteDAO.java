package ar.edu.unq.chasqui.dao;

import java.util.List;

import ar.edu.unq.chasqui.model.Fabricante;

public interface FabricanteDAO {

	public List<Fabricante> obtenerProductoresDe(Integer idVendedor);

}
