package ar.edu.unq.chasqui.view.composer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import ar.edu.unq.chasqui.model.Direccion;
import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.model.ProductoPedido;
import ar.edu.unq.chasqui.view.renders.PedidoRenderer;

@SuppressWarnings({"serial","deprecation","unused"})
public class PedidosComposer  extends GenericForwardComposer<Component>{
	
	private Datebox desde;
	private Datebox hasta;
	private Listbox listboxPedidos;
	private Button confirmarEntregabtn;
	private AnnotateDataBinder binder;
	
	private List<Pedido>pedidos;
	
	
	public void doAfterCompose(Component c) throws Exception{
		super.doAfterCompose(c);
		c.addEventListener(Events.ON_USER, new EntregaEventListener(this));
		binder = new AnnotateDataBinder(c);
		pedidos  = new ArrayList<Pedido>();
		//pedidos = crearPedidos(); 
		listboxPedidos.setItemRenderer(new PedidoRenderer((Window) c));
		binder.loadAll();
	}


	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	
	private List<Pedido> crearPedidos(){
		List<Pedido>pss = new ArrayList<Pedido>();
		
		Direccion d = new Direccion();
		d.setCalle("BRAGADO");
		d.setCodigoPostal("1875");
		d.setLocalidad("WILDE");
		d.setAltura(6314);
		Double d1= new Double(40);
		Double d2 = new Double(39.20);
		Double d3 = new Double(50);
//		Pedido p = new Pedido(1,"jfflores90@gmail.com",new Date(),d1,d2,Constantes.ESTADO_PEDIDO_ABIERTO,false);
//		Pedido p2= new Pedido(2,"jfflores90@gmail.com",new Date(),d1,d3,Constantes.ESTADO_PEDIDO_CANCELADO,false);
//		Pedido p3 = new Pedido(3,"jfflores90@gmail.com",new Date(),d1,d3,Constantes.ESTADO_PEDIDO_CONFIRMADO,true);
//		Pedido p4 = new Pedido(4,"jfflores90@gmail.com",new Date(),d1,d2,Constantes.ESTADO_PEDIDO_ENTREGADO,false);
//		Pedido p9 = new Pedido(12,"jfflores90@gmail.com",new Date(),d1,d3,Constantes.ESTADO_PEDIDO_CONFIRMADO,true);
//		Pedido p5 = new Pedido(4,"jfflores90@gmail.com",new Date(),d1,d2,Constantes.ESTADO_PEDIDO_ENTREGADO,false);
//		Pedido p6 = new Pedido(4,"jfflores90@gmail.com",new Date(),d1,d2,Constantes.ESTADO_PEDIDO_ENTREGADO,false);
//		Pedido p7 = new Pedido(4,"jfflores90@gmail.com",new Date(),d1,d2,Constantes.ESTADO_PEDIDO_ENTREGADO,false);
		
//		p.agregarUsuarioParticipante("Jorge");
//		ProductoPedido h = new ProductoPedido();
//		h.setCantidad(1);
//		h.setNombreProducto("Aceite");
//		h.setNombreVariante("1 LT");
//		h.setPrecio(30.0);
//		p.agregarProducto(h, "Jorge");
//		
//		p.setDireccionEntrega(d);
//		p2.setDireccionEntrega(d);
//		p3.setDireccionEntrega(d);
//		p4.setDireccionEntrega(d);
//		p5.setDireccionEntrega(d);
//		p6.setDireccionEntrega(d);
//		p7.setDireccionEntrega(d);
//		pss.add(p);
//		pss.add(p2);
//		pss.add(p3);
//		pss.add(p4);
//		pss.add(p5);
//		pss.add(p6);
//		pss.add(p7);
//		pss.add(p7);
//		pss.add(p7);
//		pss.add(p7);
//		pss.add(p7);
		return pss;
	}
	
	
	public void onVerPedido(Pedido p){
		HashMap<String,Object>params = new HashMap<String,Object>();
		params.put("pedido", p);
		Window w = (Window) Executions.createComponents("/pedido.zul", this.self, params);
		w.doModal();
		
	}
	
	
	
	public void onClick$confirmarEntregabtn(){
		for(Pedido p : this.pedidos){
			p.confirmarte();
		}
		
		// guardarPedido
		this.binder.loadAll();
	}
	
	
	
	
}

class EntregaEventListener implements EventListener<Event>{

	PedidosComposer composer;
	
	public EntregaEventListener(PedidosComposer c){
		this.composer = c;
	}
	
	public void onEvent(Event event) throws Exception {
		Map<String,Object> params = (Map<String,Object>) event.getData();
		Pedido p = (Pedido) params.get("pedido");
		composer.onVerPedido(p);
		
	}
	
}

