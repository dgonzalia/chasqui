package ar.edu.unq.chasqui.view.renders;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Space;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import ar.edu.unq.chasqui.model.Imagen;

public class ImagenesRender implements ListitemRenderer<Imagen>{
	
	Window varianteWindow;
	boolean lectura;
	
	public ImagenesRender(Component comp,boolean lectura){
		varianteWindow = (Window) comp;
		this.lectura = lectura;
	}
	
	
	public void render(Listitem item, Imagen img, int arg2) throws Exception {
		
		Listcell c1 = new Listcell(img.getNombre());
		Listcell c2 = new Listcell();
		Hbox hbox = new Hbox();
		Space space = new Space();
		space.setSpacing("5px");

		if(lectura){
			Toolbarbutton trashbutton = new Toolbarbutton();
			trashbutton.setImage("/imagenes/trash.png");
			trashbutton.addForward(Events.ON_CLICK, varianteWindow, Events.ON_CLICK, img);
			trashbutton.setParent(hbox);			
		}
		
		Toolbarbutton downloadbutton = new Toolbarbutton();
		downloadbutton.setImage("/imagenes/download.png");
		downloadbutton.addForward(Events.ON_CLICK, varianteWindow, Events.ON_USER, img);
		
		space.setParent(hbox);
		downloadbutton.setParent(hbox);
		hbox.setParent(c2);
		c1.setParent(item);
		c2.setParent(item);
	}

}
