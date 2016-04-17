package ar.edu.unq.chasqui.view.composer;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import ar.edu.unq.chasqui.model.CaracteristicaProductor;
import ar.edu.unq.chasqui.model.Fabricante;
import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.services.impl.FileSaver;
import ar.edu.unq.chasqui.services.interfaces.CaracteristicaService;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;

@SuppressWarnings({"serial","deprecation"})
public class ABMProductorComposer extends GenericForwardComposer<Component> {

	
	private Textbox textboxNombreProductor;
	private Textbox txtDireccion;
	private Intbox altura;
	private Combobox comboCaracteristica;
	private CaracteristicaProductor caracteristicaSeleccionada;
	private AnnotateDataBinder binder;
	private Toolbarbutton buttonGuardar;
	
	
	
	private Vendedor usuario;
	private Fabricante model;
	private UsuarioService usuarioService;
	private CaracteristicaService service;
	private List<CaracteristicaProductor>caracteristicas = new ArrayList<CaracteristicaProductor>();
	
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		usuario = (Vendedor) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME);
		model = (Fabricante) Executions.getCurrent().getArg().get("productor");
		usuarioService = (UsuarioService) SpringUtil.getBean("usuarioService");
		service = (CaracteristicaService) SpringUtil.getBean("caracteristicaService");
	
		caracteristicas = service.buscarCaracteristicasProductorBy(usuario.getId());
		
		if(model != null){
			inicializarModoLectura();
		}
		
		binder = new AnnotateDataBinder(comp);
	}
	
	
	
	

	
	
	public void inicializarModoLectura(){
		buttonGuardar.setDisabled(true);
		textboxNombreProductor.setValue(model.getNombre());
		textboxNombreProductor.setDisabled(true);
		txtDireccion.setDisabled(true);
		txtDireccion.setValue(model.getCalle());
		altura.setValue(model.getAltura());
		caracteristicaSeleccionada = model.getCaracteristica();
		comboCaracteristica.setValue(caracteristicaSeleccionada.getNombre());
		altura.setDisabled(true);
		
	}
	
	public void onClick$buttonGuardar(){
		String productor = textboxNombreProductor.getValue();
		Integer alt = altura.getValue();
		String calle = txtDireccion.getValue();
		validar(productor,alt,calle);
		model  = new Fabricante(productor);
		model.setCaracteristica(caracteristicaSeleccionada);
		model.setCalle(calle);
		model.setAltura(alt);
		usuario.agregarProductor(model);
		usuarioService.guardarUsuario(usuario);
		Events.sendEvent(Events.ON_RENDER,this.self.getParent(),null);
		this.self.detach();
	}
	
	public void onClick$botonCancelar(){
		this.self.detach();
	}
	public List<CaracteristicaProductor> getCaracteristicas() {
		return caracteristicas;
	}
	public void setCaracteristicas(List<CaracteristicaProductor> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	public CaracteristicaProductor getCaracteristicaSeleccionada() {
		return caracteristicaSeleccionada;
	}
	public void setCaracteristicaSeleccionada(CaracteristicaProductor caracteristicaSeleccionada) {
		this.caracteristicaSeleccionada = caracteristicaSeleccionada;
	}
	
	private void validar(String productor,Integer alt, String calle) {
		if(StringUtils.isEmpty(productor)){
			throw new WrongValueException(textboxNombreProductor,"El productor no debe ser vacio!");
		}
		
		if(usuario.contieneProductor(productor)){
			throw new WrongValueException("El usuario: " + usuario.getUsername() + " ya tiene el productor: " + productor );
		}
		
		if(productor.matches(".*[0-9].*")){
			throw new WrongValueException(textboxNombreProductor,"El productor debe ser un nombre sin numeros");
		}
		
		if(StringUtils.isEmpty(calle)){
			throw new WrongValueException(txtDireccion,"La calle no debe ser vacía");
		}
		if(alt == null){
			throw new WrongValueException(altura,"La altura no debe ser vacía");
		}
		
		if(caracteristicaSeleccionada == null){
			throw new WrongValueException(comboCaracteristica,"Debe seleccionar una caracteristica");
		}
		
	}
	
	
	


}
