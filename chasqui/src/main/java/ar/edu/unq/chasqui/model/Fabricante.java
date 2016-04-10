package ar.edu.unq.chasqui.model;

import java.util.ArrayList;
import java.util.List;

public class Fabricante {

	private Integer id;
	private String nombre;
	private Direccion direccion;
	private List<Producto> productos;
	private List<CaracteristicaProductor> caracteristicas;
 	
	//CONSTRUCTORs

	public Fabricante(){
		productos = new ArrayList<Producto>();
		caracteristicas = new ArrayList<CaracteristicaProductor>();
	}

	public Fabricante(String nombre){
		this.nombre = nombre;
		productos = new ArrayList<Producto>();
		caracteristicas = new ArrayList<CaracteristicaProductor>();
	}
	
	//GETs & SETs
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	
	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	
	public List<CaracteristicaProductor> getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(List<CaracteristicaProductor> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	
	
	//METHODS
	
	@Override
	public String toString(){
		return this.getNombre();
	}

	public void agregarProducto(Producto model) {
		this.productos.add(model);
		
	}

}
