package ar.edu.unq.chasqui.service.rest.request;

public class ByMedallaRequest extends ProductoRequest{

	
	private Integer idMedalla;

	public Integer getIdMedalla() {
		return idMedalla;
	}

	public void setIdMedalla(Integer idMedalla) {
		this.idMedalla = idMedalla;
	}
	
	
	@Override
	public String toString(){
		return "ByMedallaRequest [ idMedalla: "+ idMedalla + " pagina: "+ this.getPagina()+
				" precio: "+this.getPrecio()+ "cantidad de items: "+this.getCantItems()+" ]";
	}
	
	
	
}
