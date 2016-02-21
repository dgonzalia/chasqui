package ar.edu.unq.chasqui.view.composer;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.cxf.common.util.StringUtils;
import org.joda.time.DateTime;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.services.impl.FileSaver;

@SuppressWarnings({"serial","deprecation"})
public class ConfiguracionComposer extends GenericForwardComposer<Component>{
	
	private Usuario usuarioLogueado;
	private Window configuracionWindow;
	private AnnotateDataBinder binder;
	private Toolbarbutton buttonGuardar;
	private Fileupload uploadImagen;
	private Combobox comboCantidadDeKilometros;
	private Checkbox checkUtilizarMismaFecha;
	private Datebox dateProximaEntrega;
	private Textbox textboxClaveActual;
	private Textbox textboxNuevaClaveRepita;
	private Textbox textboxNuevaClave;
	private Intbox intboxMontoMinimo;
	private List<Integer>kilometros = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
	private Integer kilometroSeleccionado;
	private FileSaver fileSaver;
	private Imagen imagen;
	
	public void doAfterCompose(Component comp) throws Exception{
		usuarioLogueado =(Usuario) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME);
		if(usuarioLogueado != null){
			super.doAfterCompose(comp);
			imagen = new Imagen();
			imagen.setPath(usuarioLogueado.getPathImagen());
			fileSaver = (FileSaver) SpringUtil.getBean("fileSaver");
			binder = new AnnotateDataBinder(comp);
			kilometroSeleccionado = usuarioLogueado.getKilometroSeleccionado();
			DateTime d = new DateTime(usuarioLogueado.getFechaProximaEntrega());
			DateTime hoy = new DateTime();
			if(hoy.isBefore(d)){
				d.plusMonths(1);
				usuarioLogueado.setFechaProximaEntrega(new Date(d.getMillis()));
				dateProximaEntrega.setValue(new Date(d.getMillis()));
			}else{
				dateProximaEntrega.setValue(usuarioLogueado.getFechaProximaEntrega());
			}
			intboxMontoMinimo.setValue(usuarioLogueado.getMontoMinimoCompra());
			comp.addEventListener(Events.ON_NOTIFY, new SubirArchivoListener(this));
			binder.loadAll();			
		}
	}
	
	public void onUpload$uploadImagen(UploadEvent evt){
		Clients.showBusy("Procesando...");
		Events.echoEvent(Events.ON_NOTIFY,this.self,evt);
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
			imagen = fileSaver.guardarImagen(path +"/",usuarioLogueado.getUsername(),image.getContent().getName(),image.getContent().getByteData());
//			usuarioLogueado.setPathImagen(imagen.getPath());		
		}catch(Exception e){
			Messagebox.show("Ha ocurrido un error al subir la imagen","Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}finally{
			Clients.clearBusy();
			binder.loadAll();
		}
	}
	
	
	public void validarPassword(){
		String claveActual = textboxClaveActual.getValue();
		String nuevaClave = textboxNuevaClave.getValue();
		String nuevaClaveRepita = textboxNuevaClaveRepita.getText();
		if(StringUtils.isEmpty(claveActual) && (!StringUtils.isEmpty(nuevaClave) || !StringUtils.isEmpty(nuevaClaveRepita))){
			throw new WrongValueException(textboxClaveActual,"Por favor introduzca su contraseña actual, si desea actualizarla");
		}
		
		if(!StringUtils.isEmpty(nuevaClave) && !StringUtils.isEmpty(nuevaClaveRepita) && !nuevaClave.equals(nuevaClaveRepita)){
			WrongValueException e1 = new WrongValueException(textboxNuevaClave,"Las contraseñas no coinciden!");
			WrongValueException e2 = new WrongValueException(textboxNuevaClaveRepita,"Las contraseñas no coinciden!");
			throw new WrongValuesException(new WrongValueException[] {e1,e2});
		}
		if( !StringUtils.isEmpty(claveActual) && !claveActual.equals(usuarioLogueado.getPassword())){
			throw new WrongValueException(textboxClaveActual,"La contraseña actual es incorrecta!");			
		}
		
		if(!StringUtils.isEmpty(nuevaClave) && (!nuevaClave.matches("^[a-zA-Z0-9]*$")|| nuevaClave.length() < 8)){
		  throw new WrongValueException(textboxNuevaClave,"La nueva contraseña no cumple con los requisitos!");
		}
	}
	
	public void validacionesDeCompra(){
		Date date = dateProximaEntrega.getValue();
		Integer monto = intboxMontoMinimo.getValue();
		
		if(monto == null || monto <= 0){
			throw new WrongValueException(intboxMontoMinimo,"El monto no debe ser menor a 0!");
		}
		if(date.before(new Date())){
			throw new WrongValueException(dateProximaEntrega,"La fecha de proxima entrega debe ser posterior a la fecha actual!");
		}
		
	}
	
	
	public void onClick$buttonGuardar(){
		validarPassword();
		validacionesDeCompra();
		// guardar y cambiar la img
	}
	

	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}
	public Window getConfiguracionWindow() {
		return configuracionWindow;
	}
	public void setConfiguracionWindow(Window configuracionWindow) {
		this.configuracionWindow = configuracionWindow;
	}
	public AnnotateDataBinder getBinder() {
		return binder;
	}
	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public List<Integer> getKilometros() {
		return kilometros;
	}

	public void setKilometros(List<Integer> kilometros) {
		this.kilometros = kilometros;
	}

	public Integer getKilometroSeleccionado() {
		return kilometroSeleccionado;
	}

	public void setKilometroSeleccionado(Integer kilometroSeleccionado) {
		this.kilometroSeleccionado = kilometroSeleccionado;
	}

	public Toolbarbutton getButtonGuardar() {
		return buttonGuardar;
	}
	
	public Fileupload getUploadImagen() {
		return uploadImagen;
	}

	public void setUploadImagen(Fileupload uploadImagen) {
		this.uploadImagen = uploadImagen;
	}

	public void setButtonGuardar(Toolbarbutton buttonGuardar) {
		this.buttonGuardar = buttonGuardar;
	}

	public Combobox getComboCantidadDeKilometros() {
		return comboCantidadDeKilometros;
	}
	public void setComboCantidadDeKilometros(Combobox comboCantidadDeKilometros) {
		this.comboCantidadDeKilometros = comboCantidadDeKilometros;
	}
	public Checkbox getCheckUtilizarMismaFecha() {
		return checkUtilizarMismaFecha;
	}
	public void setCheckUtilizarMismaFecha(Checkbox checkUtilizarMismaFecha) {
		this.checkUtilizarMismaFecha = checkUtilizarMismaFecha;
	}
	public Datebox getDateProximaEntrega() {
		return dateProximaEntrega;
	}
	public void setDateProximaEntrega(Datebox dateProximaEntrega) {
		this.dateProximaEntrega = dateProximaEntrega;
	}
	public Textbox getTextboxClaveActual() {
		return textboxClaveActual;
	}
	public void setTextboxClaveActual(Textbox textboxClaveActual) {
		this.textboxClaveActual = textboxClaveActual;
	}
	public Textbox getTextboxNuevaClaveRepita() {
		return textboxNuevaClaveRepita;
	}
	public void setTextboxNuevaClaveRepita(Textbox textboxNuevaClaveRepita) {
		this.textboxNuevaClaveRepita = textboxNuevaClaveRepita;
	}
	public Textbox getTextboxNuevaClave() {
		return textboxNuevaClave;
	}
	public void setTextboxNuevaClave(Textbox textboxNuevaClave) {
		this.textboxNuevaClave = textboxNuevaClave;
	}
	public Intbox getIntboxMontoMinimo() {
		return intboxMontoMinimo;
	}
	public void setIntboxMontoMinimo(Intbox intboxMontoMinimo) {
		this.intboxMontoMinimo = intboxMontoMinimo;
	}

	public FileSaver getFileSaver() {
		return fileSaver;
	}

	public void setFileSaver(FileSaver fileSaver) {
		this.fileSaver = fileSaver;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}
	
	
	
	
	

}

class SubirArchivoListener implements EventListener<Event>{
	
	ConfiguracionComposer composer;
	public SubirArchivoListener(ConfiguracionComposer c){
		this.composer = c;
	}
	public void onEvent(Event event) throws Exception {
		composer.actualizarImagen((UploadEvent)event.getData());
		
	}
}
