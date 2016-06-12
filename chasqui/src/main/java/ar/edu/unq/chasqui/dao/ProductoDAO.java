package ar.edu.unq.chasqui.dao;

import java.util.List;

import ar.edu.unq.chasqui.model.Producto;

public interface ProductoDAO {

	public List<Producto> obtenerProductos( Integer idCategoria, Integer pagina, Integer cantidadDeItems);

}
