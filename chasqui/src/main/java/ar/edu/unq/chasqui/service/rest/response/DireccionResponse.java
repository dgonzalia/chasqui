package ar.edu.unq.chasqui.service.rest.response;

import java.io.Serializable;

import ar.edu.unq.chasqui.model.Direccion;

public class DireccionResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2706752008400147353L;
	private String calle;
	private Integer altura;
	private String localidad;
	private String codigoPostal;
	private Double latitud;
	private Double longitud;
	
	
	
	public DireccionResponse(Direccion d){
		this.altura = d.getAltura();
		this.calle = d.getCalle();
		this.localidad = d.getLocalidad();
		this.codigoPostal = d.getCodigoPostal();
		this.latitud = d.getLatitud();
		this.longitud = d.getLongitud();
	}
	
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public Integer getAltura() {
		return altura;
	}
	public void setAltura(Integer altura) {
		this.altura = altura;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public Double getLatitud() {
		return latitud;
	}
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	public Double getLongitud() {
		return longitud;
	}
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	
	

	
	
	
	
}
