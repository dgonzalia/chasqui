package ar.edu.unq.chasqui.services.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Vendedor;

public interface VendedorService {

	
	@Transactional
	public List<Vendedor> obtenerVendedores();
	
	@Transactional
	public Vendedor obtenerVendedor(String nombreVendedor);
}
