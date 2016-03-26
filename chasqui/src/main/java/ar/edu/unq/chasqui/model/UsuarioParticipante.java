package ar.edu.unq.chasqui.model;

import java.util.ArrayList;
import java.util.List;

public class UsuarioParticipante {

	private Integer id;
	private String userName;
	private List<ProductoPedido>productosEnPedido;
	
	public UsuarioParticipante(String username){
		this.userName = username;
		this.productosEnPedido = new ArrayList<ProductoPedido>();
	}
	
	public List<ProductoPedido> getProductosEnPedido() {
		return productosEnPedido;
	}

	public void setProductosEnPedido(List<ProductoPedido> productosEnPedido) {
		this.productosEnPedido = productosEnPedido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
		
}
