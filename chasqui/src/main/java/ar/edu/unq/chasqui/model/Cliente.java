package ar.edu.unq.chasqui.model;

import java.util.List;

public class Cliente extends Usuario{

	private Integer id;
	private String nombre;
	private String apellido;
	private String telefonoFijo;
	private String telefonoMovil;
	private Direccion direccionPredeterminada;
	private List<Direccion> direccionesAlternativas;
	private List<Notificacion> notificaciones;
	private Historial historialPedidos;
	private List<Pedido> pedidos;
 
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
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getTelefonoFijo() {
		return telefonoFijo;
	}
	
	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}
	
	public String getTelefonoMovil() {
		return telefonoMovil;
	}
	
	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}
	
	public Direccion getDireccionPredeterminada() {
		return direccionPredeterminada;
	}
	
	public void setDireccionPredeterminada(Direccion direccionPredeterminada) {
		this.direccionPredeterminada = direccionPredeterminada;
	}
	
	public List<Direccion> getDireccionesAlternativas() {
		return direccionesAlternativas;
	}
	
	public void setDireccionesAlternativas(List<Direccion> direccionesAlternativas) {
		this.direccionesAlternativas = direccionesAlternativas;
	}
	
	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}
	
	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}
	
	public Historial getHistorialPedidos() {
		return historialPedidos;
	}
	
	public void setHistorialPedidos(Historial historialPedidos) {
		this.historialPedidos = historialPedidos;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	
		
}
