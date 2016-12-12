package ar.edu.unq.chasqui.services.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.model.Variante;
import ar.edu.unq.chasqui.service.rest.request.ByCategoriaRequest;
import ar.edu.unq.chasqui.service.rest.request.ByMedallaRequest;
import ar.edu.unq.chasqui.service.rest.request.ByProductorRequest;
import ar.edu.unq.chasqui.service.rest.request.ByQueryRequest;
import ar.edu.unq.chasqui.service.rest.request.SinFiltroRequest;

public interface ProductoService {
	
	@Transactional
	public List<Variante>obtenerVariantesPorCategoria(ByCategoriaRequest request);
	@Transactional
	public List<Variante> obtenerVariantesPorProductor(ByProductorRequest request);
	@Transactional
	public List<Variante> obtenerVariantesPorMedalla(ByMedallaRequest request);
	@Transactional
	public List<Imagen> obtenerImagenesDe(Integer idProducto);
	@Transactional
	public List<Variante> obtenerVariantesPorNombreODescripcion(ByQueryRequest request) ;
	@Transactional
	public Variante obtenerVariantePor(Integer id);
	@Transactional
	public void modificarVariante(Variante v);
	@Transactional
	public void eliminarReservasDe(Pedido p);
	@Transactional
	public Caracteristica obtenerMedalla(Integer idMedalla);
	
	@Transactional
	public Long totalVariantesPorCategoria(ByCategoriaRequest request);
	@Transactional
	public Long totalVariantesPorProductor(ByProductorRequest request);
	@Transactional
	public Long totalVariantesPorMedalla(ByMedallaRequest request);
	@Transactional
	public Long totalVariantesPorNombreODescripcion(ByQueryRequest request);
	@Transactional
	public List<Variante> obtenerVariantesSinFiltro(SinFiltroRequest request);
	@Transactional
	public Long totalVariantesSinFiltro(SinFiltroRequest request);
	
	
}
