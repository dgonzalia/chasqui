package ar.edu.unq.chasqui.test.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.chasqui.model.Categoria;
import ar.edu.unq.chasqui.model.Fabricante;
import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.model.Vendedor;

public class VendedorTest {

	Vendedor v;
	Fabricante p;
	Categoria c;
	Producto pr;
	
	
	@Before
	public void setUp(){
		v = new Vendedor();
		p = new Fabricante();
		c = new Categoria();
		p.setNombre("productor");
		c.setNombre("categoria");
		List<Producto>productos = new ArrayList<Producto>();
		productos.add(pr);
		c.setProductos(productos);
		v.setCategorias(new ArrayList<Categoria>());
		v.setFabricantes(new ArrayList<Fabricante>());
	}
	
	
	@Test
	public void vendedorNoContieneProductor(){
		assertFalse(v.contieneProductor("productor"));
	}
	
	@Test
	public void vendedorContieneProductor(){
		v.agregarProductor(p);
		assertTrue(v.contieneProductor("productor"));
	}
	
	@Test
	public void vendedorNoContieneCategoria(){
		assertFalse(v.contieneCategoria("categoria"));
	}
	
	@Test
	public void vendedorContieneCategoria(){
		v.agregarCategoria(c);
		assertTrue(v.contieneCategoria("categoria"));
	}
	
	@Test
	public void vendedorEliminar(){
		v.eliminarProductor(p);
		assertFalse(v.contieneProductor("productor"));
	}
	
	@Test
	public void vendedorCategoria(){
		v.eliminarCategoria(c);
		assertFalse(v.contieneCategoria("categoria"));
	}
	
	@Test
	public void obtenerProductos(){
		v.agregarCategoria(c);
		assertEquals(v.obtenerProductos().size(),1);
	}
	
}
