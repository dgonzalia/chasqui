package ar.edu.unq.chasqui.view.renders;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import ar.edu.unq.chasqui.model.Fabricante;

public class ProductorRenderer implements ListitemRenderer<Fabricante>{

	Window administracionWindow;
	
	public ProductorRenderer(Component w){
		this.administracionWindow = (Window) w;
	}
	
	public void render(Listitem item, Fabricante f, int arg2) throws Exception {
		
		Listcell c1 = new Listcell(f.getNombre());
		Listcell c2 = new Listcell();
		Hbox hbox = new Hbox();
		
		
		Map<String,Object>params1 = new HashMap<String,Object>();
		Map<String,Object>params2 = new HashMap<String,Object>();
		
		params1.put("accion", "visualizar");
		params1.put("productor", f);
		Toolbarbutton ver = new Toolbarbutton();
		ver.setTooltiptext(Labels.getLabel("zk.toolbarbutton.administracion.tooltip.visualizar"));
		ver.setImage("/imagenes/eye.png");
		ver.addForward(Events.ON_CLICK, administracionWindow, Events.ON_NOTIFY, params1);
		
		params2.put("accion", "eliminar");
		params2.put("productor", f);
		Toolbarbutton eliminar = new Toolbarbutton();
		eliminar.setImage("/imagenes/detach.png");
		eliminar.setTooltiptext(Labels.getLabel("zk.toolbarbutton.administracion.tooltip.eliminar"));
		eliminar.addForward(Events.ON_CLICK, administracionWindow, Events.ON_NOTIFY, params2);
		
		ver.setParent(hbox);
		eliminar.setParent(hbox);
		hbox.setParent(c2);

		c1.setParent(item);
		c2.setParent(item);
	}

}
