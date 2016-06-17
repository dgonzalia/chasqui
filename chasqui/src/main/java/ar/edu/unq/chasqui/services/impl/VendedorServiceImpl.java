package ar.edu.unq.chasqui.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.dao.VendedorDAO;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.services.interfaces.VendedorService;

public class VendedorServiceImpl implements VendedorService{

	@Autowired
	VendedorDAO vendedorDAO;
	
	
	@Override
	public List<Vendedor> obtenerVendedores() {
		return vendedorDAO.obtenerVendedores();
	}


	
	
	
	
	
	
	public VendedorDAO getVendedorDAO() {
		return vendedorDAO;
	}
	public void setVendedorDAO(VendedorDAO vendedorDAO) {
		this.vendedorDAO = vendedorDAO;
	}
	
	
	
	
	

}
