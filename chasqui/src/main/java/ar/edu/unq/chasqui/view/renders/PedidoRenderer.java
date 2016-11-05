package ar.edu.unq.chasqui.view.renders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Space;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.view.composer.Constantes;

public class PedidoRenderer implements ListitemRenderer<Pedido>{

	private Window pedidoWindow;
	public PedidoRenderer(Window w){
		pedidoWindow = w;
	}
	
	public void render(Listitem item, final Pedido p, int arg2) throws Exception {
		
		Listcell c1 = new Listcell(String.valueOf(p.getId()));
		Listcell c2 = new Listcell(p.getUsuarioCreador());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date d = new Date(p.getFechaCreacion().getMillis());
		Listcell c3 = new Listcell(format.format(d));
		Listcell c4 = new Listcell(String.valueOf(p.getMontoMinimo()));
		Listcell c5 = new Listcell(String.valueOf(p.getMontoActual()));
		final Checkbox c = new Checkbox("Entregado");
		if(p.getMontoMinimo() < p.getMontoActual()){
			c5.setStyle("color:green;");
		}else{
			c5.setStyle("color:red;");
		}
		Listcell c6 = new Listcell(p.getEstado());
		String estado = p.getEstado();
		if(estado.equals(Constantes.ESTADO_PEDIDO_CONFIRMADO)){
			c6.setStyle("color:blue;");
		}
		if(estado.equals(Constantes.ESTADO_PEDIDO_CANCELADO)){
			c6.setStyle("color:red;");
		}
		if(estado.equals(Constantes.ESTADO_PEDIDO_ENTREGADO)){
			c6.setStyle("color:green");
			c.setChecked(true);
		}
		
		String direccion= "";
		if(p.getDireccionEntrega() != null){
			direccion = p.getDireccionEntrega().getCalle() +" "+ p.getDireccionEntrega().getAltura();
		}
		Listcell c7 = new Listcell(direccion);
		Listcell c8 = new Listcell();
		
		Toolbarbutton b = new Toolbarbutton("Ver Producto");
		b.setTooltiptext("Ver Producto");
		b.setImage("/imagenes/eye.png");
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("pedido", p);
		b.addForward(Events.ON_CLICK, pedidoWindow, Events.ON_USER, params);
		
		c.addEventListener(Events.ON_CHECK,new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				if(c.isChecked()){
					p.setEstado(Constantes.ESTADO_PEDIDO_ENTREGADO);
				}else{
					p.setEstado(Constantes.ESTADO_PEDIDO_CONFIRMADO);
				}				
			}
		});
		
		Space s = new Space();
		s.setSpacing("10px");
		
		if(!p.getAlterable()){
			c.setDisabled(true);
		}
		
		
		Hlayout hbox = new Hlayout();
		b.setParent(hbox);
		s.setParent(hbox);
		c.setParent(hbox);
		hbox.setParent(c8);
		c1.setParent(item);
		c2.setParent(item);
		c3.setParent(item);
		c4.setParent(item);
		c5.setParent(item);
		c6.setParent(item);
		c7.setParent(item);
		c8.setParent(item);
		
		
	}

}
