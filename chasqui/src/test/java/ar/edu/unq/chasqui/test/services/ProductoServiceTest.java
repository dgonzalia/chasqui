package ar.edu.unq.chasqui.test.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.unq.chasqui.exceptions.CaracteristicaInexistenteException;
import ar.edu.unq.chasqui.exceptions.RequestIncorrectoException;
import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.model.Variante;
import ar.edu.unq.chasqui.service.rest.request.ByCategoriaRequest;
import ar.edu.unq.chasqui.service.rest.request.ByMedallaRequest;
import ar.edu.unq.chasqui.service.rest.request.ByProductorRequest;
import ar.edu.unq.chasqui.services.interfaces.ProductoService;

@ContextConfiguration(locations = {
"file:src/test/java/dataSource-Test.xml",
"file:src/main/resources/beans/service-beans.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductoServiceTest extends GenericSetUp {

	
	@Autowired ProductoService productoService;
	
	Producto p;
	Variante v;
	ByCategoriaRequest byCategoriaRequest;
	ByProductorRequest byProductorRequest;
	ByMedallaRequest byMedallaRequest;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		p = new Producto();
		p.setFabricante(f);
		p.setNombre("mermelada");
		p.setCategoria(c);
		List<Caracteristica> cs = new ArrayList<Caracteristica>();
		cs.add(caracteristica);
		p.setCaracteristicas(cs);
		v = new Variante();
		v.setNombre("frutilla");
		v.setDescripcion("descripcion");
		v.setDestacado(false);
		v.setStock(4);
		v.setPrecio(40.0);
		v.setProducto(p);
		v.setCantidadReservada(0);
		List<Variante>vs = new ArrayList<Variante>();
		vs.add(v);
		p.setVariantes(vs);
		List<Producto>ps = new ArrayList<Producto>();
		ps.add(p);
		c.setProductos(ps);
		f.setProductos(ps);
		byCategoriaRequest = new ByCategoriaRequest();
		byCategoriaRequest.setCantItems(1);
		byCategoriaRequest.setIdCategoria(c.getId());
		byCategoriaRequest.setPrecio("Down");
		byCategoriaRequest.setPagina(1);
		
		byProductorRequest = new ByProductorRequest();
		byProductorRequest.setCantItems(1);
		byProductorRequest.setIdProductor(f.getId());
		byProductorRequest.setPrecio("Down");
		byProductorRequest.setPagina(1);
		
		byMedallaRequest = new ByMedallaRequest();
		byMedallaRequest.setCantItems(1);
		byMedallaRequest.setIdMedalla(caracteristica.getId());
		byMedallaRequest.setPrecio("Down");
		byMedallaRequest.setPagina(1);
		byMedallaRequest.setIdVendedor(u2.getId());
		usuarioService.guardarUsuario(u2);		
	}
	
	@After
	public void tearDown(){
		super.tearDown();
	}
	
	
	//busqueda de variantes por categoria
	
	@Test(expected = RequestIncorrectoException.class)
	public void testObtenerVariantesPorCategoriaOrdenamientoInvalido(){
		byCategoriaRequest.setPrecio(null);
		productoService.obtenerVariantesPorCategoria(byCategoriaRequest);
	}
	
	
	@Test(expected = RequestIncorrectoException.class)
	public void testObtenerVariantesPorCategoriaPaginaNull(){
		byCategoriaRequest.setPagina(null);
		productoService.obtenerVariantesPorCategoria(byCategoriaRequest);
	}
	
	@Test(expected = RequestIncorrectoException.class)
	public void testObtenerVariantesPorCategoriaCantItemsNull(){
		byCategoriaRequest.setCantItems(null);
		productoService.obtenerVariantesPorCategoria(byCategoriaRequest);
	}
	
	@Test(expected = RequestIncorrectoException.class)
	public void testObtenerVariantesPorIdCategoriaNull(){
		byCategoriaRequest.setIdCategoria(null);
		productoService.obtenerVariantesPorCategoria(byCategoriaRequest);
	}
	
	@Test()
	public void testObtenerVariantesPorCategoria(){
		List<Variante>vs =productoService.obtenerVariantesPorCategoria(byCategoriaRequest);
		assertTrue(vs != null);
		assertEquals(vs.get(0).getNombre(),"frutilla");
	}
	
	
	@Test()
	public void testObtenerVariantesPorCategoriaSinStock(){
		v.setCantidadReservada(4);
		usuarioService.guardarUsuario(u2);
		List<Variante>vs =productoService.obtenerVariantesPorCategoria(byCategoriaRequest);
		assertTrue(vs != null);
		assertEquals(vs.size(),0);
	}
	
	// busqueda de variantes por productor
	
	
	@Test(expected = RequestIncorrectoException.class)
	public void testObtenerVariantesPorMedallaOrdenamientoInvalido(){
		byProductorRequest.setPrecio(null);
		productoService.obtenerVariantesPorProductor(byProductorRequest);
	}
	
	
	@Test(expected = RequestIncorrectoException.class)
	public void testObtenerVariantesPorProductorPaginaNull(){
		byProductorRequest.setPagina(null);
		productoService.obtenerVariantesPorProductor(byProductorRequest);
	}
	
	@Test(expected = RequestIncorrectoException.class)
	public void testObtenerVariantesPorProductorCantItemsNull(){
		byProductorRequest.setCantItems(null);
		productoService.obtenerVariantesPorProductor(byProductorRequest);
	}
	
	@Test(expected = RequestIncorrectoException.class)
	public void testObtenerVariantesPorIdProductorNull(){
		byProductorRequest.setIdProductor(null);
		productoService.obtenerVariantesPorProductor(byProductorRequest);
	}
	
	@Test()
	public void testObtenerVariantesPorProductor(){
		List<Variante>vs =productoService.obtenerVariantesPorProductor(byProductorRequest);
		assertTrue(vs != null);
		assertEquals(vs.get(0).getNombre(),"frutilla");
	}
	
	@Test()
	public void testObtenerVariantesPorProductorSinStock(){
		v.setCantidadReservada(4);
		usuarioService.guardarUsuario(u2);
		List<Variante>vs =productoService.obtenerVariantesPorProductor(byProductorRequest);
		assertTrue(vs != null);
		assertEquals(vs.size(),0);
	}
	
	// OBTENER VARIANTES POR MEDALLAS
	
	@Test(expected = RequestIncorrectoException.class)
	public void testObtenerVariantesPorProductorOrdenamientoInvalido(){
		byMedallaRequest.setPrecio(null);
		productoService.obtenerVariantesPorMedalla(byMedallaRequest);
	}
	
	
	@Test(expected = RequestIncorrectoException.class)
	public void testObtenerVariantesPorMedallaPaginaNull(){
		byMedallaRequest.setPagina(null);
		productoService.obtenerVariantesPorMedalla(byMedallaRequest);
	}
	
	@Test(expected = RequestIncorrectoException.class)
	public void testObtenerVariantesPorMedallaCantItemsNull(){
		byMedallaRequest.setCantItems(null);
		productoService.obtenerVariantesPorMedalla(byMedallaRequest);
	}
	
	@Test(expected = RequestIncorrectoException.class)
	public void testObtenerVariantesPorIdMedallaNull(){
		byMedallaRequest.setIdMedalla(null);
		productoService.obtenerVariantesPorMedalla(byMedallaRequest);
	}
	
	@Test(expected = RequestIncorrectoException.class)
	public void testObtenerVariantesPorMedallaVendedorNull(){
		byMedallaRequest.setIdVendedor(null);
		productoService.obtenerVariantesPorMedalla(byMedallaRequest);
	}
	
	@Test()
	public void testObtenerVariantesPorMedalla(){
		List<Variante>vs =productoService.obtenerVariantesPorMedalla(byMedallaRequest);
		assertTrue(vs != null);
		assertEquals(vs.get(0).getNombre(),"frutilla");
	}
	
	@Test
	public void testObtenerVariantesPorMedallaSinStock(){
		v.setCantidadReservada(4);
		usuarioService.guardarUsuario(u2);
		List<Variante>vs =productoService.obtenerVariantesPorMedalla(byMedallaRequest);
		assertTrue(vs != null);
		assertEquals(vs.size(),0);
	}
	
	@Test
	public void testObtenerVariantePorId(){
		Variante v2 = productoService.obtenerVariantePor(v.getId());
		assertEquals(v2.getId(),v.getId());
		assertEquals(v2.getNombre(),v.getNombre());
	}
	
	@Test
	public void testObtenerMedallaPorId(){
		Caracteristica c2 = productoService.obtenerMedalla(caracteristica.getId());
		assertEquals(c2.getId(),caracteristica.getId());
		assertEquals(c2.getNombre(),caracteristica.getNombre());
	}
	
	@Test(expected=CaracteristicaInexistenteException.class)
	public void testObtenerMedallaPorIdInvalida(){
		productoService.obtenerMedalla(10);
	}
	
	
}
