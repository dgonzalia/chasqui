package ar.edu.unq.chasqui.view.composer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Image;

@SuppressWarnings("deprecation")
public class ZonasComposer extends GenericForwardComposer<Component> {

	
	private Image imagen;
	private AnnotateDataBinder binder;
	
	public void doAfterCompose(Component c) throws Exception{
		super.doAfterCompose(c);
		imagen.setSrc("/imagenes/subirImagen.png");
		binder = new AnnotateDataBinder(c);
		binder.loadAll();
	}

	
	
	
	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}
	
	
	
	
	
	
}
