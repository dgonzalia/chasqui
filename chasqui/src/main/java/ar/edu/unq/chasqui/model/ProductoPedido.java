package ar.edu.unq.chasqui.model;

public class ProductoPedido {

	
	private String precio;
	private String nombreProducto_nombreVariante;
	private Integer cantidad;
	
	
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getNombreProducto_nombreVariante() {
		return nombreProducto_nombreVariante;
	}
	public void setNombreProducto_nombreVariante(String nombreProducto_nombreVariante) {
		this.nombreProducto_nombreVariante = nombreProducto_nombreVariante;
	}
	
	
	
	
	
}
