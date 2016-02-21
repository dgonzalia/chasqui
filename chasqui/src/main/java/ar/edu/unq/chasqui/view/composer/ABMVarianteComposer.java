package ar.edu.unq.chasqui.view.composer;

import javax.servlet.ServletContext;

import org.zkforge.ckez.CKeditor;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.model.Variante;
import ar.edu.unq.chasqui.services.impl.FileSaver;

@SuppressWarnings({"serial","deprecation"})
public class ABMVarianteComposer  extends GenericForwardComposer<Component>{

	
	private AnnotateDataBinder binder;
	
	private FileSaver fileSaver;
	private Usuario usuarioLogueado;
	private Variante model;
	private Producto producto;
	private Imagen imagen;
	
	private Intbox intboxPrecio;
	private Intbox intboxStock;
	private Textbox textboxNombre;
	private CKeditor ckEditor;
	private Fileupload uploadImagen;
	
	public void doAfterCompose(Component c) throws Exception{
		super.doAfterCompose(c);
		usuarioLogueado =(Usuario) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME);
		model = (Variante) Executions.getCurrent().getArg().get("variante");
		fileSaver = (FileSaver) SpringUtil.getBean("fileSaver");
		producto = (Producto) Executions.getCurrent().getArg().get("producto");
		imagen = new Imagen();
		if(model == null){
			model = new Variante();
			imagen.setPath(usuarioLogueado.getPathImagen());
		}else{
			inicializarModoLectura();
		}
		binder = new AnnotateDataBinder(c);
		binder.loadAll();
	}
	
	
	public void inicializarModoLectura(){
		imagen.setPath(model.getPathDeImagen());
		intboxPrecio.setValue(model.getPrecio());
		intboxStock.setValue(model.getStock());
		textboxNombre.setValue(model.getNombre());
		ckEditor.setValue(model.getDescripcion());
		intboxPrecio.setDisabled(true);
		intboxStock.setDisabled(true);
		textboxNombre.setDisabled(true);
		uploadImagen.setDisabled(true);
		ckEditor.setCustomConfigurationsPath("/js/ckEditorReadOnly.js");
		
	}
	
	public void onUpload$uploadImagen(UploadEvent evt){
		Media media = evt.getMedia();
		Image image = new Image();
		if (media instanceof org.zkoss.image.Image) {
			image.setContent((org.zkoss.image.Image) media);
		} else {
			Messagebox.show("El archivo no es una imagen","Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		ServletContext context = Sessions.getCurrent().getWebApp().getServletContext();
		String path = context.getRealPath("/imagenes/");
		imagen = fileSaver.guardarImagen(path +"/",usuarioLogueado.getUsername(),image.getContent().getName(),image.getContent().getByteData());
		binder.loadAll();
	}

	
	public void onClick$botonCancelar(){
		this.self.detach();
	}
	
	public void onClick$botonGuardar(){
		
	}

	
	
	public Imagen getImagen() {
		return imagen;
	}


	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}
	
	
	
	
	
}
