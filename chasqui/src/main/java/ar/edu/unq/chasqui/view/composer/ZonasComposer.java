package ar.edu.unq.chasqui.view.composer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.cxf.common.util.StringUtils;
import org.joda.time.DateTime;
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
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.model.Zona;
import ar.edu.unq.chasqui.services.impl.FileSaver;

@SuppressWarnings({"deprecation","unused"})
public class ZonasComposer extends GenericForwardComposer<Component> {

	
	private Image imgMapa;
	private AnnotateDataBinder binder;
	private Textbox nombreZona;
	private Button btnGuardar;
	private Button btnLimpiar;
	private Button guardar;
	private Button cancelar;
	private Fileupload uploadImagen;
	private Datebox fechaEntrega;
	private Textbox txtDescripcion;
	
	private Zona zonaSeleccionada;
	private List<Zona> zonas;
	private Usuario usuario;
	private FileSaver fileSaver;
	
	
	
	
	public void doAfterCompose(Component c) throws Exception{
		super.doAfterCompose(c);
		usuario = (Usuario) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME);
		fileSaver = (FileSaver) SpringUtil.getBean("fileSaver");
		c.addEventListener(Events.ON_NOTIFY,new SubirImagenListener(this));
		zonas = new ArrayList<Zona>();
		binder = new AnnotateDataBinder(c);
		binder.loadAll();
	}

	public void onEliminarZona(){
		zonas.remove(zonaSeleccionada);
		this.binder.loadAll();
	}
	
	
	public void onClick$btnLimpiar(){
		limpiarCampos();
		this.binder.loadAll();
	}
	
	private void limpiarCampos(){
		txtDescripcion.setValue(null);
		fechaEntrega.setValue(null);
		nombreZona.setValue(null);
	}

	public void onClick$btnGuardar(){
		String zona = nombreZona.getValue();
		Date d = fechaEntrega.getValue();
		String msg = txtDescripcion.getValue();
		validarZona(zona,d,msg);
		DateTime fecha = new DateTime(d.getTime());
		Zona z = new Zona(zona,fecha,msg);
		z.setNombre(zona);
		z.setIdUsuario(usuario.getId());
		z.setFechaEntrega(fecha);
		z.setDescripcion(msg);
		
		
		zonas.add(z);
		limpiarCampos();
		this.binder.loadAll();
		//GUARDAR EN DB
		
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
			ServletContext context = Sessions.getCurrent().getWebApp().getServletContext();
			String path = context.getRealPath("/imagenes/");
			Imagen imagen = fileSaver.guardarImagen(path +"/",usuario.getUsername(),image.getContent().getName(),image.getContent().getByteData());
			imgMapa.setSrc(imagen.getPath());		
		}catch(Exception e){
			Messagebox.show("Ha ocurrido un error al subir la imagen","Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}finally{
			Clients.clearBusy();
			binder.loadAll();
		}
	}
	
	
	public void onUpload$uploadImagen(UploadEvent evt){
		Clients.showBusy("Procesando...");
		Events.echoEvent(Events.ON_NOTIFY,this.self,evt);
	}
	
	
	private void validarZona(String zona, Date t,String msg){
		if(StringUtils.isEmpty(zona)){
			throw new WrongValueException(nombreZona,"La zona no debe ser vacia");
		}
		if(estaEnLista(zona)){
			throw new WrongValueException(nombreZona,"La zona: '"+zona+ "' ya se encuentra en la lista" );
		}
		if(t == null){
			throw new WrongValueException(fechaEntrega, "La fecha de entrega no debe ser vacia!");
		}
		DateTime hoy = new DateTime();
		if(hoy.isAfter(t.getTime())){
			throw new WrongValueException(fechaEntrega, "La fecha de entrega debe ser posterior a la fecha actual");
		}
		if(StringUtils.isEmpty(msg)){
			txtDescripcion.setFocus(true);
			throw new WrongValueException(txtDescripcion,"El mensaje de próxima fecha de entrega no debe ser vacío");
		}
	}
	
	
	private boolean estaEnLista(String zona){
		for(Zona z : zonas){
			if(z.getNombre().equalsIgnoreCase(zona)){
				return true;
			}
		}
		return false;
	}
	
	
	public void onClick$guardar(){
		alert("guardar");
	}
	
	public void onClick$cancelar(){
		this.self.detach();
	}
	
	
	public Zona getZonaSeleccionada() {
		return zonaSeleccionada;
	}

	public void setZonaSeleccionada(Zona zonaSeleccionada) {
		this.zonaSeleccionada = zonaSeleccionada;
	}

	public List<Zona> getZonas() {
		return zonas;
	}

	public void setZonas(List<Zona> zonas) {
		this.zonas = zonas;
	}
	
	
	
	
	
}

class SubirImagenListener implements EventListener<Event>{

	ZonasComposer composer;
	public SubirImagenListener(ZonasComposer c){
		this.composer = c;
	}
	
	public void onEvent(Event event) throws Exception {
		composer.actualizarImagen((UploadEvent)event.getData());
		
	}
	
}
