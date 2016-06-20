package ar.edu.unq.chasqui.dao;

import java.util.List;

import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Producto;

public interface ProductoDAO {

	public List<Producto> obtenerProductosPorCategoria( Integer idCategoria, Integer pagina, Integer cantidadDeItems);

	public List<Producto> obtenerProductosPorProductor(Integer idProductor, Integer pagina, Integer cantItems);

	public List<Producto> obtenerProductosPorMedalla(Integer idMedalla, Integer pagina, Integer cantItems);

	public List<Imagen> obtenerImagenesDe(Integer idProducto);

}
