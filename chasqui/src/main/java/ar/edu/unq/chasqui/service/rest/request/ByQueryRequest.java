package ar.edu.unq.chasqui.service.rest.request;

public class ByQueryRequest extends ProductoRequest {

	
	private String query;
	private Integer idVendedor;
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Integer getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Integer idVendedor) {
		this.idVendedor = idVendedor;
	}
	
	
	
	
}
