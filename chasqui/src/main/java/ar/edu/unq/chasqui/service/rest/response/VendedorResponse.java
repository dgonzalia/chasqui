package ar.edu.unq.chasqui.service.rest.response;

import java.io.Serializable;

import ar.edu.unq.chasqui.model.Vendedor;

public class VendedorResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4642765076419773900L;
	private Integer id;
	private String nombre;
	
	
	
	public VendedorResponse(Vendedor v){
		id = v.getId();
		nombre = v.getUsername();
	}
	
	
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
