package ar.edu.unq.chasqui.model;

import java.util.List;

public class UsuarioParticipante {

	private String userName;
	private List<ProductoPedido>productosEnPedido;
	
	
	

	public List<ProductoPedido> getProductosEnPedido() {
		return productosEnPedido;
	}

	public void setProductosEnPedido(List<ProductoPedido> productosEnPedido) {
		this.productosEnPedido = productosEnPedido;
	}
	
	
	
	
	
	
	
}
