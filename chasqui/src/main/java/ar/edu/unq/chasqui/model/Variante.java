package ar.edu.unq.chasqui.model;

import java.util.List;

public class Variante {

	private Integer id;
	private List<Imagen> imagenes;
	private String descripcion;
	private Integer stock;
	private Integer cantidadReservada;
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

	public Integer getCantidadReservada() {
		return cantidadReservada;
	}

	public void setCantidadReservada(Integer cantidadReservada) {
		this.cantidadReservada = cantidadReservada;
	}

	public boolean tieneStockParaReservar(Integer cantidad) {
		return this.getStock() - (this.getCantidadReservada() + cantidad)> 0;
	}

	
	public boolean tieneStock(){
		return this.getStock() - this.getCantidadReservada() > 0;
	}
	
	public Integer getIdVendedor() {
		return this.producto.getFabricante().getIdVendedor();
	}

	public void reservarCantidad(Integer cantidad) {
		cantidadReservada += cantidad;
		
	}

	public void eliminarReserva(Integer cantidad) {
		cantidadReservada -= cantidad;
		
	}	
		
	@Override
	public String toString(){
		return "Variante: [ id:"+id+" Stock:"+stock+"Reservados:"+cantidadReservada+
		 "Precio:"+precio+" Nombre:"+nombre+" idProducto:"+ producto.getId()+" ]";
		
	}
}
