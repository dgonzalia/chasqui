package ar.edu.unq.chasqui.view.composer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.CaracteristicaProductor;
import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.services.impl.FileSaver;
import ar.edu.unq.chasqui.services.interfaces.CaracteristicaService;
import ar.edu.unq.chasqui.view.renders.CaracteristicaProductorRenderer;
import ar.edu.unq.chasqui.view.renders.CaracteristicaRenderer;

@SuppressWarnings("deprecation")
public class CaracteristicaComposer extends GenericForwardComposer<Component>{

	private Window caracteristicaWindow;
	private AnnotateDataBinder binder;
	private Image imgIcon;
	private Fileupload uploadImagen;
	private Button agregar;
	private Listbox ltbCaracteristicas;
	private Textbox txtbCaracteristica;

	private FileSaver fileSaver;
	private Vendedor usuario;
	private Imagen imagen;
	private List<Caracteristica>caracteristicas;
	
	
	private Button agregarProductor;
	private Listbox ltbCaracteristicasProductor;
	private List<CaracteristicaProductor> caracteristicasProductor;
	private Fileupload uploadImagenProductor;
	private Image imgIconProductor;
	private Imagen imagenProductor;
	private Textbox txtbCaracteristicaProductor;
	
	
	private CaracteristicaService service;
	
	public void doAfterCompose(Component c) throws Exception{
		super.doAfterCompose(c); 
		usuario = (Vendedor) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME);
		binder = new AnnotateDataBinder(c);
		service = (CaracteristicaService) SpringUtil.getBean("caracteristicaService");
		caracteristicas = service.buscarCaracteristicasProductoBy(usuario.getId());
		caracteristicasProductor = service.buscarCaracteristicasProductorBy(usuario.getId());
		ltbCaracteristicas.setItemRenderer(new CaracteristicaRenderer((Window) c,false));
		ltbCaracteristicasProductor.setItemRenderer(new CaracteristicaProductorRenderer((Window) c,false));
		fileSaver= (FileSaver) SpringUtil.getBean("fileSaver");
		// buscar caracteristicas productor por vendedor
		// "          "            producto "    "
		c.addEventListener(Events.ON_NOTIFY, new ArchivoListener(this));
		c.addEventListener(Events.ON_UPLOAD, new ArchivoListener(this));
		this.binder.loadAll();
	}
	
	
	
	public void onClick$agregar(){
		String carac = txtbCaracteristica.getValue();
		validarCaracteristica();
		Caracteristica c = new Caracteristica();
		c.setNombre(carac);
		c.setPathImagen(imagen.getPath());
		c.setIdVendedor(usuario.getId());
		caracteristicas.add(c);
		txtbCaracteristica.setValue(null);
		imagen = null;
		imgIcon.setSrc(null);
		this.binder.loadAll();
	}
	
	public void onClick$agregarProductor(){
		String carac = txtbCaracteristicaProductor.getValue();
		validarCaracteristicaProductor();
		CaracteristicaProductor c = new CaracteristicaProductor();
		c.setNombre(carac);
		c.setPathImagen(imagenProductor.getPath());
		c.setIdVendedor(usuario.getId());
		caracteristicasProductor.add(c);
		txtbCaracteristicaProductor.setValue(null);
		imagenProductor = null;
		imgIconProductor.setSrc(null);
		this.binder.loadAll();
	}
	
	private void validarCaracteristicaProductor(){
		String c = txtbCaracteristicaProductor.getValue();
		if(StringUtils.isEmpty(c)){
			throw new WrongValueException(txtbCaracteristicaProductor,"La caracteristica del productor no debe ser vacia");
		}
		if(imagenProductor == null){
			throw new WrongValueException(imgIconProductor,"Debe asociar un icono a la caracteristica");
		}
	}
	
	private void validarCaracteristica(){
		String c = txtbCaracteristica.getValue();
		if(StringUtils.isEmpty(c)){
			throw new WrongValueException(txtbCaracteristica,"La caracteristica del productor no debe ser vacia");
		}
		if(imagen == null){
			throw new WrongValueException(imgIcon,"Debe asociar un icono a la caracteristica");
		}
	}
	
	public void onUpload$uploadImagenProductor(UploadEvent evt){
		Clients.showBusy("Procesando...");
		Events.echoEvent(Events.ON_UPLOAD,this.self,evt);
	}
	

	public void onUpload$uploadImagen(UploadEvent evt){
		Clients.showBusy("Procesando...");
		Events.echoEvent(Events.ON_NOTIFY,this.self,evt);
	}
	
	public void onClick$guardarCaracteristicaProductor(){
		service.guardarCaracteristicaProductor(caracteristicasProductor);
		alert("Las caracteristicas De los productores se han guardado correctamente");
	}
	
	public void onClick$guardarCaracteristica(){
		service.guardaCaracteristicasProducto(caracteristicas);
		alert("Las caracteristicas de los producto se han guardado correctamento");
	}
	
	public void actualizarImagen(UploadEvent evt){
		try{
			Media media = evt.getMedia();
			Image image = new Image();
			if (media instanceof org.zkoss.image.Image) {
				image.setContent((org.zkoss.image.Image) media);
			} else {
				Messagebox.show("El archivo no es una imagen","Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			byte[] icono = fileSaver.iconizar(image.getContent().getStreamData(),image.getContent().getName().split("\\.")[1]);
			ServletContext context = Sessions.getCurrent().getWebApp().getServletContext();
			String path = context.getRealPath("/imagenes/");
			imagen = fileSaver.guardarImagen(path +"/",usuario.getUsername(),image.getContent().getName(),icono);
			imgIcon.setSrc(imagen.getPath());
			this.binder.loadAll();
		}catch(Exception e){
			Messagebox.show("Ha ocurrido un error al subir la imagen","Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}finally{
			Clients.clearBusy();
			binder.loadAll();
		}
	}
	
	
	public void actualizarImagenProductor(UploadEvent evt){
		try{
			Media media = evt.getMedia();
			Image image = new Image();
			if (media instanceof org.zkoss.image.Image) {
				image.setContent((org.zkoss.image.Image) media);
			} else {
				Messagebox.show("El archivo no es una imagen","Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			byte[] icono = fileSaver.iconizar(image.getContent().getStreamData(),image.getContent().getName().split("\\.")[1]);
			ServletContext context = Sessions.getCurrent().getWebApp().getServletContext();
			String path = context.getRealPath("/imagenes/");
			imagenProductor = fileSaver.guardarImagen(path +"/",usuario.getUsername(),image.getContent().getName(),icono);
			imgIconProductor.setSrc(imagenProductor.getPath());
			this.binder.loadAll();
		}catch(Exception e){
			Messagebox.show("Ha ocurrido un error al subir la imagen","Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}finally{
			Clients.clearBusy();
			binder.loadAll();
		}
	}


	

	public List<Caracteristica> getCaracteristicas() {
		return caracteristicas;
	}
	public void setCaracteristicas(List<Caracteristica> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	public List<CaracteristicaProductor> getCaracteristicasProductor() {
		return caracteristicasProductor;
	}
	public void setCaracteristicasProductor(List<CaracteristicaProductor> caracteristicasProductor) {
		this.caracteristicasProductor = caracteristicasProductor;
	}
	
	
	
	
	
	
	
}








final class ArchivoListener implements EventListener<Event>{
	
	CaracteristicaComposer composer;
	public ArchivoListener(CaracteristicaComposer c){
		this.composer = c;
	}
	public void onEvent(Event event) throws Exception {
		if(event.getName().equals(Events.ON_NOTIFY)){
			composer.actualizarImagen((UploadEvent)event.getData());			
		}
		if(event.getName().equals(Events.ON_UPLOAD)){
			composer.actualizarImagenProductor((UploadEvent)event.getData());
		}
	}
}
	
