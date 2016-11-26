package ar.edu.unq.chasqui.test.services;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.CaracteristicaProductor;
import ar.edu.unq.chasqui.model.Categoria;
import ar.edu.unq.chasqui.model.Fabricante;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.security.Encrypter;
import ar.edu.unq.chasqui.services.interfaces.CaracteristicaService;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;

public class GenericSetUp {

	Vendedor u2;
	Caracteristica caracteristica;
	CaracteristicaProductor caracteristicaProductor;
	List<Caracteristica>caracs;
	Fabricante f;
	Categoria c;
	@Autowired Encrypter encrypter;
	@Autowired UsuarioService usuarioService;
	@Autowired CaracteristicaService caracteristicaService;
	
	public void setUp() throws Exception{
		DateTime cierre = new DateTime().plusDays(1);
		u2 = new Vendedor();
		u2.setUsername("MatLock");
		u2.setNombre("MatLock - Nombre");
		u2.setPassword(encrypter.encrypt("federico"));
		u2.setEmail("floresfederico_993@hotmail.com");
		u2.setIsRoot(false);
		u2.setMontoMinimoPedido(213);
		u2.setFechaCierrePedido(cierre);
		
		List<Categoria> cs = new ArrayList<Categoria>();
		c = new Categoria();
		c.setNombre("Categoria");
		cs.add(c);
		f = new Fabricante();
		f.setNombre("fabricante");
		List<Fabricante> fss = new ArrayList<Fabricante>();
		fss.add(f);
		u2.setFabricantes(fss);
		u2.setFabricantes(fss);
		u2.setCategorias(cs);
		c.setVendedor(u2);
		usuarioService.guardarUsuario(u2);
		
		List<CaracteristicaProductor>caracProductor = new ArrayList<CaracteristicaProductor>();
		caracs = new ArrayList<Caracteristica>();
		
		caracteristicaProductor = new CaracteristicaProductor();
		caracteristicaProductor.setNombre("caracteristicaProductor");
		caracteristicaProductor.setEliminada(false);
		caracteristica = new Caracteristica();
		caracteristica.setNombre("caracteristica");
		caracteristica.setEliminada(false);
		caracProductor.add(caracteristicaProductor);
		caracs.add(caracteristica);
		
		caracteristicaService.guardaCaracteristicasProducto(caracs);
		caracteristicaService.guardarCaracteristicaProductor(caracProductor);
	}
	
	
	public void tearDown(){
		usuarioService.deleteObject(u2);
		caracteristicaService.eliminarCaracteristica(caracteristica);
		caracteristicaService.eliminarCaracteristicaProductor(caracteristicaProductor);
	}
}
