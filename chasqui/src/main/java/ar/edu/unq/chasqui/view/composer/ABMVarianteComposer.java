package ar.edu.unq.chasqui.view.composer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.cxf.common.util.StringUtils;
import org.zkforge.ckez.CKeditor;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.model.Variante;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.services.impl.FileSaver;
import ar.edu.unq.chasqui.view.genericEvents.Refresher;
import ar.edu.unq.chasqui.view.renders.ImagenesRender;

@SuppressWarnings({"serial","deprecation"})
public class ABMVarianteComposer  extends GenericForwardComposer<Component> implements Refresher{

	
	private AnnotateDataBinder binder;
	
	private FileSaver fileSaver;
	private Vendedor usuarioLogueado;
	private Variante model;
	private Producto producto;
	private List<Imagen> imagenes;
	
	private Doublebox doubleboxPrecio;
	private Intbox intboxStock;
	private Textbox textboxNombre;
	private CKeditor ckEditor;
	private Fileupload uploadImagen;
	private Listbox listImagenes;
	private boolean nuevaVariante=false;
	
	
	public void doAfterCompose(Component c) throws Exception{
		super.doAfterCompose(c);
		usuarioLogueado =(Vendedor) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME);
		model = (Variante) Executions.getCurrent().getArg().get("variante");
		fileSaver = (FileSaver) SpringUtil.getBean("fileSaver");
		producto = (Producto) Executions.getCurrent().getArg().get("producto");
		boolean lectura = (Boolean) Executions.getCurrent().getArg().get("lectura");
		c.addEventListener(Events.ON_CLICK, new BorrarImagenEventListener(this));
		c.addEventListener(Events.ON_USER, new DescargarImagenEventListener(this));
		imagenes = new ArrayList<Imagen>();
		
		if(model == null){
			model = new Variante();
			nuevaVariante = true;
		}
		if(!lectura){
			llenarCampos();
			listImagenes.setItemRenderer(new ImagenesRender(c,true,this));
		}else{
			inicializarModoLectura();
			listImagenes.setItemRenderer(new ImagenesRender(c,false,this));
		}
		binder = new AnnotateDataBinder(c);
		binder.loadAll();
	}
	
	
	public void llenarCampos(){
		imagenes.addAll(model.getImagenes());
		doubleboxPrecio.setValue(model.getPrecio());
		intboxStock.setValue(model.getStock());
		textboxNombre.setValue(model.getNombre());
		ckEditor.setValue(model.getDescripcion());
		
	}
	
	public void inicializarModoLectura(){
		llenarCampos();
		listImagenes.setDisabled(true);
		doubleboxPrecio.setDisabled(true);
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
				Messagebox.show("El archivo no es una imagen o es demasiado grande","Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			if(imagenes.size() == 3){
				Messagebox.show("Solo está permitido hasta 3 imagenes por variedad del producto.");
			}
			
			ServletContext context = Sessions.getCurrent().getWebApp().getServletContext();
			String path = context.getRealPath("/imagenes/");
			Imagen imagen = fileSaver.guardarImagen(path ,usuarioLogueado.getUsername(),image.getContent().getName(),image.getContent().getByteData());
			imagen.setNombre(image.getContent().getName());
			imagen.setPreview(false);
			imagenes.add(imagen);
			binder.loadAll();
	}

	
	
	public void refresh() {
		this.binder.loadAll();
		
	}
	
	public void eliminarImagen(Imagen img){
		imagenes.remove(img);
		refresh();
	}
	
	public void descargarImagen(Imagen img) throws IOException{
		Filedownload.save(img.getPath(), null);
	}

	
	public void onClick$botonCancelar(){
		this.self.detach();
	}
	
	public void onClick$botonGuardar(){
		ejecutarValidaciones();
		model.setDescripcion(ckEditor.getValue());
		model.setImagenes(imagenes);
		model.setNombre(textboxNombre.getValue());
		model.setStock(intboxStock.getValue());
		model.setPrecio(doubleboxPrecio.getValue());
		model.setCantidadReservada(0);
		model.setProducto(producto);
		model.setDestacado(false);
		if(nuevaVariante){
			producto.getVariantes().add(model);			
		}
		Events.sendEvent(Events.ON_RENDER,this.self.getParent(),null);
		this.self.detach();
	}

	
	private void ejecutarValidaciones(){
		Double precio = doubleboxPrecio.getValue();
		Integer stock = intboxStock.getValue();
		String descripcion = ckEditor.getValue();
		String nombre = textboxNombre.getValue();
		
		if(imagenes.isEmpty()){
			throw new WrongValueException(listImagenes,"se debe agregar al menos una imagen");
		}
		if(precio == null || precio < 0){
			throw new WrongValueException(doubleboxPrecio,"El precio debe ser mayor a 0");
		}
		if(stock == null || stock < 0){
			throw new WrongValueException(intboxStock,"El Stock debe ser mayor a 0");
		}
		if(StringUtils.isEmpty(descripcion)){
			throw new WrongValueException(ckEditor,"La descripción no debe ser vacia");
		}
		if(StringUtils.isEmpty(nombre)){
			throw new WrongValueException(textboxNombre,"El nombre no debe ser vacio");
		}
		
		if(descripcion.length() > 255){
			throw new WrongValueException(ckEditor,"La descripción es demasiado larga");
		}
		int previews = 0;
		for(Imagen i : imagenes){
			if(i.getPreview()){
				previews++;
			}
		}
		if(previews > 1){
			throw new WrongValueException(listImagenes,"No se puede elegir mas de una imagen de previsualización");
		}
		
	}

	public List<Imagen> getImagenes() {
		return imagenes;
	}


	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}
	
}

class BorrarImagenEventListener implements EventListener<Event>{

	ABMVarianteComposer composer;
	public BorrarImagenEventListener(ABMVarianteComposer composer){
		this.composer = composer;
	}
	
	public void onEvent(Event event) throws Exception {
		Imagen img = (Imagen) event.getData();
		composer.eliminarImagen(img);
	}
	
}


class DescargarImagenEventListener implements EventListener<Event>{
	ABMVarianteComposer composer;
	public DescargarImagenEventListener(ABMVarianteComposer composer){
		this.composer = composer;
	}
	public void onEvent(Event event) throws Exception {
		Imagen img = (Imagen) event.getData();
		composer.descargarImagen(img);
		
	}
}
