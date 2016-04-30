package ar.edu.unq.chasqui.model;

import java.util.List;

public class Historial {

	private Integer id;
	private String usuario;
	private List<Pedido> pedidos;
	
	//GETs & SETs 

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
}
