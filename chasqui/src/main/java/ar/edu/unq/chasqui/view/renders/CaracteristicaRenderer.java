package ar.edu.unq.chasqui.view.renders;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import ar.edu.unq.chasqui.model.Caracteristica;

public class CaracteristicaRenderer implements ListitemRenderer<Caracteristica>{

	
	Window window;
	boolean lectura;
	
	public CaracteristicaRenderer(Window c,boolean lectura){
		window = c;
		this.lectura = lectura;
	}
	
	public void render(Listitem item, Caracteristica c, int arg2) throws Exception {
		Listcell c1 = new Listcell();
		Listcell c2 = new Listcell();
		Listcell c3 = new Listcell();
		Toolbarbutton b = new Toolbarbutton();
		b.setImage("/imagenes/detach.png");
		b.setDisabled(lectura);
		
		b.addForward(Events.ON_CLICK,window ,Events.ON_USER , c);
		c1.setLabel(c.getNombre());
		c2.setImage(c.getPathImagen());
		b.setParent(c3);
		
		c1.setParent(item);
		c2.setParent(item);
		c3.setParent(item);
		
	}
	
}
