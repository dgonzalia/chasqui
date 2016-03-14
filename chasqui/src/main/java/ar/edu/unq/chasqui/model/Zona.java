package ar.edu.unq.chasqui.model;

import org.joda.time.DateTime;

public class Zona {

	private Integer id;
	private Integer idUsuario;
	private String nombre;
	private DateTime fechaEntrega;
	private String descripcion;
	
	//GETs & SETs
	
	public Zona(String zona, DateTime fecha, String msg) {
		this.nombre = zona;
		this.fechaEntrega = fecha;
		this.descripcion = msg;
	}
	
	public Zona(){}

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
	
	public DateTime getFechaEntrega() {
		return fechaEntrega;
	}
	
	public void setFechaEntrega(DateTime fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
	

	
}
