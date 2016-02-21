package ar.edu.unq.chasqui.model;

public class Caracteristica {
	
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	public Caracteristica(){}
	public Caracteristica(String nombre){
		this.nombre = nombre;
	}
	
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
