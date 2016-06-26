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
	private String latitud;
	private String longitud;
	private Boolean predeterminada;
	private String departamento;
	private String alias;
	
	
	
	public DireccionResponse(){}
	public DireccionResponse(Direccion d){
		this.altura = d.getAltura();
		this.calle = d.getCalle();
		this.localidad = d.getLocalidad();
		this.codigoPostal = d.getCodigoPostal();
		this.latitud = d.getLatitud();
		this.longitud = d.getLongitud();
		alias = d.getAlias();
		departamento = d.getDepartamento();
		predeterminada = d.getPredeterminada();
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
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public Boolean getPredeterminada() {
		return predeterminada;
	}
	public void setPredeterminada(Boolean predeterminada) {
		this.predeterminada = predeterminada;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	
	
	

	
	
	
	
}
