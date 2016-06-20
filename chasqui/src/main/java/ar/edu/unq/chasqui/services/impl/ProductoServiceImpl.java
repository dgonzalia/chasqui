package ar.edu.unq.chasqui.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.dao.ProductoDAO;
import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.services.interfaces.ProductoService;

public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoDAO productoDAO;
	
	
	@Override
	public List<Producto> obtenerProductosPorCategoria(Integer idCategoria, Integer pagina,
			Integer cantidadDeItems) {
		return productoDAO.obtenerProductosPorCategoria(idCategoria,pagina, cantidadDeItems);
	}

	@Override
	public List<Producto> obtenerProductosPorProductor(Integer idProductor, Integer pagina, Integer cantItems) {
		return productoDAO.obtenerProductosPorProductor(idProductor, pagina, cantItems);
	}
	
	@Override
	public List<Producto> obtenerProductosPorMedalla(Integer idMedalla, Integer pagina, Integer cantItems) {
		return productoDAO.obtenerProductosPorMedalla(idMedalla, pagina, cantItems);
	}

	@Override
	public List<Imagen> obtenerImagenesDe(Integer idProducto) {
		return productoDAO.obtenerImagenesDe(idProducto);
	}

}
