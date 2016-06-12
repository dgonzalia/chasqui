package ar.edu.unq.chasqui.model;

import org.apache.cxf.common.util.StringUtils;

import ar.edu.unq.chasqui.service.rest.request.DireccionRequest;

public class Direccion {

	private Integer id;
	private String calle;
	private Integer altura;
	private String localidad;
	private String codigoPostal;
	private Double latitud;
	private Double longitud;
	
	
	//GETs & SETs
	
	public Direccion(DireccionRequest direccion) {
		latitud = direccion.getLatitud();
		longitud = direccion.getLongitud();
		localidad = direccion.getLocalidad();
		altura = direccion.getAltura();
		calle = direccion.getCalle();
		codigoPostal = direccion.getCodigoPostal();
		
	}
	
	public Direccion(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public void modificarCon(DireccionRequest direccion) {
		if(!StringUtils.isEmpty(direccion.getCalle())){
			this.calle = direccion.getCalle();
		}
		if(!StringUtils.isEmpty(direccion.getCodigoPostal())){
			this.codigoPostal = direccion.getCodigoPostal();
		}
		if(!StringUtils.isEmpty(direccion.getLocalidad())){
			this.localidad = direccion.getLocalidad();
		}
		if(direccion.getAltura() != null){
			this.altura = direccion.getAltura();
		}
		if(direccion.getLatitud() != null){
			this.latitud = direccion.getLatitud();
		}
		if(direccion.getLongitud() != null){
			this.longitud = direccion.getLongitud();
		}
		
	}
	

	
}
