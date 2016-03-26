package ar.edu.unq.chasqui.model;

import java.util.Date;

public class PedidoGrupal extends Pedido{

	
	public PedidoGrupal(int i, String string, Date date, Double j, Double k,
			String estadoPedidoAbierto,Boolean alterable,String descripcion) {
		super(i, string, date, j, k, estadoPedidoAbierto,alterable);
		this.descripcion=descripcion;
	}


	private String descripcion;
	
	//GETs & SETs
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
	
	
	//METHODS
	
	public void agregarMiembro () {
		//TODO
	}
	
	
}
