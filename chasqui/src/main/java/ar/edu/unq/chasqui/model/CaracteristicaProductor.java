package ar.edu.unq.chasqui.model;

import ar.edu.unq.chasqui.services.interfaces.ICaracteristica;

public class CaracteristicaProductor implements ICaracteristica{

	private Integer id;
	private Integer idVendedor;
	private String nombre;
	private String pathImagen;
	private String descripcion;
	private Boolean eliminada;

	//CONSTRUCTORs
	
	public CaracteristicaProductor(){}
	
	public CaracteristicaProductor(String nombre){
		this.nombre = nombre;
	}
	
	//GETs & SETs
	
	
	public Boolean getEliminada() {
		return eliminada;
	}
	
	public void setEliminada(Boolean eliminada) {
		this.eliminada = eliminada;
	}

	
	public Integer getId() {
		return id;
	}	

	public Integer getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Integer idVendedor) {
		this.idVendedor = idVendedor;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
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
	
	
	
	//METHODS
	
	@Override
	public String toString(){
		return this.getNombre();
	}

	@Override
	public boolean equals(Object obj){
		if( obj == null){
			return false;
		}
		if(! (obj instanceof CaracteristicaProductor)){
			return false;
		}
		if(((CaracteristicaProductor) obj).getNombre().equalsIgnoreCase(this.nombre)){
			return true;
		}
		return false;
	}
}
