package ar.edu.unq.chasqui.dao;

import java.util.List;

import ar.edu.unq.chasqui.model.Vendedor;

public interface VendedorDAO {
	
	public List<Vendedor> obtenerVendedores();

	public Vendedor obtenerVendedor(String nombreVendedor);

}
