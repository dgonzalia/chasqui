package ar.edu.unq.chasqui.service.rest.response;

import java.io.Serializable;

import ar.edu.unq.chasqui.model.Caracteristica;

public class CaracteristicaResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8267960233576142542L;
	
	private String nombre;
	private String pathImagen;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPathImagen() {
		return pathImagen;
	}
	public void setPathImagen(String pathImagen) {
		this.pathImagen = pathImagen;
	}
	
	
	public CaracteristicaResponse(Caracteristica c){
		this.pathImagen = c.getPathImagen();
		this.nombre = c.getNombre();
	}
	
	
	
}
