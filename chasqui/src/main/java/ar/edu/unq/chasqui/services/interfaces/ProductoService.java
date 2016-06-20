package ar.edu.unq.chasqui.services.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Producto;

public interface ProductoService {
	
	@Transactional
	public List<Producto>obtenerProductosPorCategoria(Integer idCategoria,Integer pagina, Integer cantidadDeItems);
	@Transactional
	public List<Producto> obtenerProductosPorProductor(Integer idProductor, Integer pagina, Integer cantItems);
	@Transactional
	public List<Producto> obtenerProductosPorMedalla(Integer idMedalla, Integer pagina, Integer cantItems);
	@Transactional
	public List<Imagen> obtenerImagenesDe(Integer idProducto);
	
}
