package ar.edu.unq.chasqui.service.rest.request;

import java.io.Serializable;

public class ByProductorRequest extends ProductoRequest implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8487612983961613791L;
	private Integer idProductor;

	
	
	
	
	
	public Integer getIdProductor() {
		return idProductor;
	}

	public void setIdProductor(Integer idProductor) {
		this.idProductor = idProductor;
	}
	
	
	
	
	
}
