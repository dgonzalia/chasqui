package ar.edu.unq.chasqui.model;

import java.util.List;

public class Variante {

	private Integer id;
	private List<Imagen> imagenes;
	private String descripcion;
	private Integer stock;
	private Double precio;
	private String nombre;
	private Producto producto;
	
	
	//GETs & SETs
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getStock() {
		return stock;
	}
	
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Double getPrecio() {
		return precio;
	}
	
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Imagen> getImagenes() {
		return imagenes;
	}
	
	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
		
	
}
