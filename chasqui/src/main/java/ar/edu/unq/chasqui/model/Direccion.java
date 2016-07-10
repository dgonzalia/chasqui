package ar.edu.unq.chasqui.model;

import org.apache.cxf.common.util.StringUtils;

import ar.edu.unq.chasqui.service.rest.request.DireccionRequest;

public class Direccion {

	private Integer id;
	private String calle;
	private Integer altura;
	private String localidad;
	private String codigoPostal;
	private String alias;
	private String departamento;
	private String latitud;
	private String longitud;
	private Boolean predeterminada;
	
	
	//GETs & SETs
	
	public Direccion(DireccionRequest direccion) {
		latitud = direccion.getLatitud();
		longitud = direccion.getLongitud();
		localidad = direccion.getLocalidad();
		altura = direccion.getAltura();
		calle = direccion.getCalle();
		codigoPostal = direccion.getCodigoPostal();
		departamento = direccion.getDepartamento();
		codigoPostal = direccion.getCodigoPostal();
		predeterminada = direccion.getPredeterminada();
		
		
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
	
	
	public Boolean getPredeterminada() {
		return predeterminada;
	}

	public void setPredeterminada(Boolean determinada) {
		this.predeterminada = determinada;
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
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getDepartamento() {
		return departamento;
	}
	
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
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
		if(direccion.getAlias() != null){
			this.alias = direccion.getAlias();
		}
		if(direccion.getDepartamento() != null){
			this.departamento = direccion.getDepartamento();
		}
		
	}
	
	
	

	
}
