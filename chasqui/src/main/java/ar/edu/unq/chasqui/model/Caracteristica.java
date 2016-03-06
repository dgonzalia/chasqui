package ar.edu.unq.chasqui.model;

public class Caracteristica {
	
	private Integer id;
	private String nombre;

	//CONSTRUCTORs
	
	public Caracteristica(){}
	
	public Caracteristica(String nombre){
		this.nombre = nombre;
	}
	
	//GETs & SETs
	
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
	

}
