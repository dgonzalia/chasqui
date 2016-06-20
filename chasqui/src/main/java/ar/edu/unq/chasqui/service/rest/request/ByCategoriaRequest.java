package ar.edu.unq.chasqui.service.rest.request;

public class ByCategoriaRequest extends ProductoRequest {

	
	private Integer idCategoria;
	
	public Integer getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
}
