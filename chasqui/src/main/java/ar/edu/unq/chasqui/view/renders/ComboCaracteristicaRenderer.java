package ar.edu.unq.chasqui.view.renders;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import ar.edu.unq.chasqui.services.interfaces.ICaracteristica;

public class ComboCaracteristicaRenderer implements ComboitemRenderer<ICaracteristica> {

	public void render(Comboitem item, ICaracteristica c, int arg2) throws Exception {
		item.setImage(c.getPathImagen());
		item.setLabel("   " +c.getNombre());
		
	}

}
