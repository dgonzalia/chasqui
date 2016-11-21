package ar.edu.unq.chasqui.test.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.chasqui.model.Fabricante;
import ar.edu.unq.chasqui.model.Producto;

public class FabricanteTest {

	
	Fabricante productor;
	Fabricante productorEliminar;
	Producto p;
	
	@Before
	public void setUp(){
		productor = new Fabricante();
		p = new Producto();
		productorEliminar = new Fabricante();
		productor.setProductos(new ArrayList<Producto>());
		productorEliminar.setProductos(new ArrayList<Producto>());
		productorEliminar.agregarProducto(p);
	}
	
	@Test
	public void testAgregarProducto(){
		assertEquals(productor.getProductos().size(),0);
		productor.agregarProducto(p);
		assertEquals(productor.getProductos().size(),1);
	}
	
	@Test
	public void testEliminarProducto(){
		assertEquals(productorEliminar.getProductos().size(),1);
		productorEliminar.eliminarProducto(p);
		assertEquals(productorEliminar.getProductos().size(),0);
	}
	
}
