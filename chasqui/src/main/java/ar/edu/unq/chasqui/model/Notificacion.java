package ar.edu.unq.chasqui.model;

import org.joda.time.DateTime;

public class Notificacion {

	private Integer id;
	private DateTime fecha;
	private Usuario usuarioOrigen;
	private Usuario usuarioDestino;
	private String mensaje;
	private String estado;
	
	//GETs & SETs
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public DateTime getFecha() {
		return fecha;
	}

	public void setFecha(DateTime fecha) {
		this.fecha = fecha;
	}
	
	public Usuario getUsuarioOrigen() {
		return usuarioOrigen;
	}
	
	public void setUsuarioOrigen(Usuario usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}
	
	public Usuario getUsuarioDestino() {
		return usuarioDestino;
	}
	
	public void setUsuarioDestino(Usuario usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
