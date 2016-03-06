package ar.edu.unq.chasqui.model;

import java.util.List;

import org.joda.time.DateTime;

public class Pedido {
	
	private Integer id;
	private String estado;
	private Cliente usuarioCreador;
	private DateTime fechaCreacion;
	private Direccion direccionEntrega;
	private Integer montoMinimo;
	private Integer montoActual;
	private List<Producto> productos;
	
	//GETs & SETs
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public Cliente getUsuarioCreador() {
		return usuarioCreador;
	}
	
	public void setUsuarioCreador(Cliente usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
	
	public DateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(DateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public Direccion getDireccionEntrega() {
		return direccionEntrega;
	}
	
	public void setDireccionEntrega(Direccion direccionEntrega) {
		this.direccionEntrega = direccionEntrega;
	}
	
	public Integer getMontoMinimo() {
		return montoMinimo;
	}
	
	public void setMontoMinimo(Integer montoMinimo) {
		this.montoMinimo = montoMinimo;
	}
	
	public Integer getMontoActual() {
		return montoActual;
	}
	
	public void setMontoActual(Integer montoActual) {
		this.montoActual = montoActual;
	}
	
	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	
	//METHODS 
	
	public void editarPedido () {
		//TODO
	}


}
