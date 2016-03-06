package ar.edu.unq.chasqui.view.renders;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import ar.edu.unq.chasqui.model.Cliente;

public class UsuarioRenderer implements ListitemRenderer<Cliente>{

	Window usuariosActualesWindow;
	public UsuarioRenderer(Window c){
		usuariosActualesWindow = c;
	}
	
	public void render(Listitem item, Cliente u, int arg2) throws Exception {
		
		Listcell c1 = new Listcell(u.getUsername());
		Listcell c2 = new Listcell();
		Hbox hbox = new Hbox();
		Toolbarbutton editar = new Toolbarbutton();
		Toolbarbutton eliminar = new Toolbarbutton();
		editar.setTooltiptext(Labels.getLabel("zk.toolbarbutton.administracion.tooltip.editar"));
		editar.setImage("/imagenes/editar.png");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accion", "editar");
		map.put("usuario", u);
		editar.addForward(Events.ON_CLICK, usuariosActualesWindow, Events.ON_NOTIFY, map); 
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("accion", "eliminar");
		map2.put("usuario", u);
		eliminar.setImage("/imagenes/detach.png");
		eliminar.setTooltiptext(Labels.getLabel("zk.toolbarbutton.administracion.tooltip.eliminar"));
		eliminar.addForward(Events.ON_CLICK, usuariosActualesWindow, Events.ON_NOTIFY,map2);
		c1.setParent(item);
		editar.setParent(hbox);
		eliminar.setParent(hbox);
		hbox.setParent(c2);
		c2.setParent(item);
		
	}

	

	
		
		
	

}
