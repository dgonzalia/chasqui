package ar.edu.unq.chasqui.model;

public class Variante {

	private Integer id;
	private String pathDeImagen;
	private String descripcion;
	private Integer stock;
	private Integer precio;
	private String nombre;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPathDeImagen() {
		return pathDeImagen;
	}
	public void setPathDeImagen(String pathDeImagen) {
		this.pathDeImagen = pathDeImagen;
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
	public Integer getPrecio() {
		return precio;
	}
	public void setPrecio(Integer precio) {
		this.precio = precio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
