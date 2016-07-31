package ar.edu.unq.chasqui.services.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Variante;

public interface ProductoService {
	
	@Transactional
	public List<Variante>obtenerVariantesPorCategoria(Integer idCategoria,Integer pagina, Integer cantidadDeItems);
	@Transactional
	public List<Variante> obtenerVariantesPorProductor(Integer idProductor, Integer pagina, Integer cantItems);
	@Transactional
	public List<Variante> obtenerVariantesPorMedalla(Integer idMedalla, Integer pagina, Integer cantItems);
	@Transactional
	public List<Imagen> obtenerImagenesDe(Integer idProducto);
	@Transactional
	public List<Variante> obtenerVariantesPorNombreODescripcion(String param,Integer pagina,Integer cantItems,Integer idVendedor) ;
	@Transactional
	public Variante obtenerVariantePor(Integer id);
	@Transactional
	public void modificarVariante(Variante v);
	
}
