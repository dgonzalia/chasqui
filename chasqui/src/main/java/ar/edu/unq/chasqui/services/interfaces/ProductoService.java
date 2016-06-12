package ar.edu.unq.chasqui.services.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Producto;

public interface ProductoService {
	
	@Transactional(readOnly=true)
	public List<Producto>obtenerProductos(Integer idCategoria,Integer pagina, Integer cantidadDeItems);
	
}
