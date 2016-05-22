package ar.edu.unq.chasqui.model;

import java.util.List;

public class Cliente extends Usuario{

	
	private String nombre;
	private String apellido;
	private Integer telefonoFijo;
	private Integer telefonoMovil;
	private Direccion direccionPredeterminada;
	private List<Direccion> direccionesAlternativas;
	private List<Notificacion> notificaciones;
	private Historial historialPedidos;
	private List<Pedido> pedidos;
	
	// Atributos necesarios para la integracion con spring security
	private String rol = "ROLE_USER";
	private String token;
 
	//GETs & SETs
	
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
	
	public Integer getTelefonoFijo() {
		return telefonoFijo;
	}
	
	public void setTelefonoFijo(Integer telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}
	
	public Integer getTelefonoMovil() {
		return telefonoMovil;
	}
	
	public void setTelefonoMovil(Integer telefonoMovil) {
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

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
	
	
	
	
		
}
