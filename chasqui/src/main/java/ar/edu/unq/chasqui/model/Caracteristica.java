package ar.edu.unq.chasqui.model;

import ar.edu.unq.chasqui.services.interfaces.ICaracteristica;

public class Caracteristica implements ICaracteristica{
	
	private Integer id;
	private Integer idVendedor;
	private String nombre;
	private String pathImagen;
	private String descripcion;
	//CONSTRUCTORs
	
	public Caracteristica(){}
	
	public Caracteristica(String nombre){
		this.nombre = nombre;
	}
	
	//GETs & SETs
		
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
	
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
	
	
	
	//METHODS

	@Override
	public boolean equals(Object obj){
		if( obj == null){
			return false;
		}
		if(! (obj instanceof Caracteristica)){
			return false;
		}
		if(((Caracteristica) obj).getNombre().equalsIgnoreCase(this.nombre)){
			return true;
		}
		return false;
	}
	
	public String toString(){
		return nombre;
	}
	

}
