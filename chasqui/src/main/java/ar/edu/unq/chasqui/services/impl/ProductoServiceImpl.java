package ar.edu.unq.chasqui.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.dao.ProductoDAO;
import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.services.interfaces.ProductoService;

public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoDAO productoDAO;
	
	
	@Override
	public List<Producto> obtenerProductos(Integer idCategoria, Integer pagina,
			Integer cantidadDeItems) {
		return productoDAO.obtenerProductos(idCategoria,pagina, cantidadDeItems);
	}

}
