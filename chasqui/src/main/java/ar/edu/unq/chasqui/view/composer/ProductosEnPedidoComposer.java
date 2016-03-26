package ar.edu.unq.chasqui.view.composer;

import java.util.HashMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;

import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.model.ProductoPedido;

public class ProductosEnPedidoComposer extends GenericForwardComposer<Component>{
	
	private AnnotateDataBinder binder;
	private HashMap<String,ProductoPedido> pedidos;
	private Double total = 0.0;
	
	@SuppressWarnings("deprecation")
	public void doAfterCompose(Component c) throws Exception{
		super.doAfterCompose(c);
		binder = new AnnotateDataBinder(c);
		Pedido p = (Pedido) Executions.getCurrent().getArg().get("pedido");
		pedidos = p.ordernarByUsuario();
		for(ProductoPedido t : pedidos.values()){
			total =+ t.getPrecio();
			
		}
		this.binder.loadAll();
		
		
	}


	public HashMap<String, ProductoPedido> getPedidos() {
		return pedidos;
	}


	public void setPedidos(HashMap<String, ProductoPedido> pedidos) {
		this.pedidos = pedidos;
	}


	public Double getTotal() {
		return total;
	}


	public void setTotal(Double total) {
		this.total = total;
	}
	
	
	
	
	

}
