package ar.edu.unq.chasqui.dao;

import java.util.List;

import ar.edu.unq.chasqui.model.Producto;

public interface ProductoDAO {

	public List<Producto> obtenerProductosPorCategoria( Integer idCategoria, Integer pagina, Integer cantidadDeItems);

}
