package ar.edu.unq.chasqui.service.rest.request;

import java.io.Serializable;

public class ConfirmarPedidoRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821567228734738867L;
	
	private Integer idPedido;
	private Integer idDireccion;
	
	public Integer getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}
	public Integer getIdDireccion() {
		return idDireccion;
	}
	public void setIdDireccion(Integer idDireccion) {
		this.idDireccion = idDireccion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
