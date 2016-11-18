package ar.edu.unq.chasqui.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.aspect.Auditada;
import ar.edu.unq.chasqui.dao.UsuarioDAO;
import ar.edu.unq.chasqui.dao.VendedorDAO;
import ar.edu.unq.chasqui.exceptions.VendedorInexistenteException;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.services.interfaces.VendedorService;

@Auditada
public class VendedorServiceImpl implements VendedorService{

	@Autowired
	VendedorDAO vendedorDAO;
	
	
	@Override
	public List<Vendedor> obtenerVendedores() {
		return vendedorDAO.obtenerVendedores();
	}


	@Override
	public Vendedor obtenerVendedor(String nombreVendedor) {
		Vendedor v = vendedorDAO.obtenerVendedor(nombreVendedor);
		if(v == null){
			throw new VendedorInexistenteException("No existe el vendedor: "+nombreVendedor );
		}
		return v;
	}
	
	
	public VendedorDAO getVendedorDAO() {
		return vendedorDAO;
	}
	public void setVendedorDAO(VendedorDAO vendedorDAO) {
		this.vendedorDAO = vendedorDAO;
	}	
	
	

}
