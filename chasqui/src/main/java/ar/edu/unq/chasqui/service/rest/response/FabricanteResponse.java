package ar.edu.unq.chasqui.service.rest.response;

import java.io.Serializable;

import ar.edu.unq.chasqui.model.Fabricante;

public class FabricanteResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -938795357704648003L;
	
	private Integer idProductor;
	private String nombreProductor;
	private String pathImagen;
	
	
	
	public FabricanteResponse(Fabricante f) {
		idProductor = f.getId();
		nombreProductor = f.getNombre();
		pathImagen = f.getPathImagen();
	}
	
	public Integer getIdProductor() {
		return idProductor;
	}
	public void setIdProductor(Integer idProductor) {
		this.idProductor = idProductor;
	}
	public String getNombreProductor() {
		return nombreProductor;
	}
	public void setNombreProductor(String nombreProductor) {
		this.nombreProductor = nombreProductor;
	}
	public String getPathImagen() {
		return pathImagen;
	}
	public void setPathImagen(String pathImagen) {
		this.pathImagen = pathImagen;
	}
	
	
	
	

}
