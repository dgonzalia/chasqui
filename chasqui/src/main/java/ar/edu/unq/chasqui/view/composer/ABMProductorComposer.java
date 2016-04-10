package ar.edu.unq.chasqui.view.composer;


import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import ar.edu.unq.chasqui.model.Fabricante;
import ar.edu.unq.chasqui.model.Vendedor;

@SuppressWarnings({"serial","deprecation"})
public class ABMProductorComposer extends GenericForwardComposer<Component> {

	
	private Textbox textboxNombreProductor;
	private AnnotateDataBinder binder;
	private Toolbarbutton buttonGuardar;
	private Vendedor usuario;
	private Fabricante model;
	
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		usuario = (Vendedor) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME);
		model = (Fabricante) Executions.getCurrent().getArg().get("productor");
		if(model != null){
			inicializarModoLectura();
		}
		binder = new AnnotateDataBinder(comp);
	}
	
	
	public void inicializarModoLectura(){
		buttonGuardar.setDisabled(true);
		textboxNombreProductor.setValue(model.getNombre());
		textboxNombreProductor.setDisabled(true);
	}
	
	public void onClick$buttonGuardar(){
		String productor = textboxNombreProductor.getValue();
		validar(productor);
		model  = new Fabricante(productor);
		usuario.agregarProductor(model);
		
		Events.sendEvent(Events.ON_RENDER,this.self.getParent(),null);
		this.self.detach();
	}
	
	public void onClick$botonCancelar(){
		this.self.detach();
	}


	private void validar(String productor) {
		if(StringUtils.isEmpty(productor)){
			throw new WrongValueException(textboxNombreProductor,"El productor no debe ser vacio!");
		}
		
		if(usuario.contieneProductor(productor)){
			throw new WrongValueException("El usuario: " + usuario.getUsername() + " ya tiene el productor: " + productor );
		}
		
		if(productor.matches(".*[0-9].*")){
			throw new WrongValueException(textboxNombreProductor,"El productor debe ser un nombre sin numeros");
		}
		
	}
	
	
	
}
