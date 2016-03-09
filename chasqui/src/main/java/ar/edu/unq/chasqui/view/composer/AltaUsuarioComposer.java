package ar.edu.unq.chasqui.view.composer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.cxf.common.util.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Vendedor;

@SuppressWarnings({"serial","deprecation"})
public class AltaUsuarioComposer extends GenericForwardComposer<Component> {

	@SuppressWarnings("unused")
	private Button buttonGuardar;
	private Textbox textboxEmail;
	private Textbox textboxContraseñaRepita;
	private Textbox textboxContraseña;
	private Textbox textboxUsername;
	private Window usuariosActualesWindow;
	private AnnotateDataBinder binder;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		comp.addEventListener(Events.ON_NOTIFY, new GuardarUsuarioEventListener(this));
		comp.addEventListener(Events.ON_USER, new GuardarUsuarioEventListener(this));
		binder.loadAll();
	}
	
	
	public void onClick$buttonGuardar(){
		validacionesParaGuardar();
		this.bloquearPantalla("Guardando Nuevo Usuario...");
		Events.echoEvent(Events.ON_NOTIFY,self,null);
	}
	
	private void validacionesParaGuardar() {
		String username = textboxUsername.getValue();
		String email = textboxEmail.getValue();
		if(StringUtils.isEmpty(username)){
			throw new WrongValueException(textboxUsername,"El usuario no deber ser vacio!");
		}
		
		if(email != null && !EmailValidator.getInstance().isValid(email)){
			throw new WrongValueException(textboxEmail,"Por favor ingrese un email valido.");
		}
		
		validarPassword();
	}

	
	private void validarPassword(){
		String nuevaClave = textboxContraseña.getValue();
		String nuevaClaveRepita = textboxContraseñaRepita.getText();		
		if(!StringUtils.isEmpty(nuevaClaveRepita) && !nuevaClave.equals(nuevaClaveRepita)){
			WrongValueException e1 = new WrongValueException(textboxContraseña,"Las contraseñas no coinciden!");
			WrongValueException e2 = new WrongValueException(textboxContraseñaRepita,"Las contraseñas no coinciden!");
			throw new WrongValuesException(new WrongValueException[] {e1,e2});
		}		
		if(!StringUtils.isEmpty(nuevaClave) && (!nuevaClave.matches("^[a-zA-Z0-9]*$")|| nuevaClave.length() < 8)){
		  throw new WrongValueException(textboxContraseña,"La nueva contraseña no cumple con los requisitos!");
		}
	}
	

	public void guardar(){
		try{ 
			// guardar
			String username = textboxUsername.getValue();
			String email = textboxEmail.getValue();
			String pwd = textboxContraseña.getValue();
			// encriptar
			Vendedor v = new Vendedor(username,email,pwd);
			Map<String,Object>params = new HashMap<String,Object>();
			params.put("usuario", v);
			params.put("accion", "crear");
			Events.sendEvent(Events.ON_NOTIFY, usuariosActualesWindow, params);
		}catch(Exception e){
			alert(e.getMessage());
		}finally{
			desbloquearPantalla();
			limpiarCampos();
		}
	}
	
	public void limpiarCampos(){
		textboxEmail.setValue(null);
		textboxContraseñaRepita.setValue(null);
		textboxContraseña.setValue(null);
		textboxUsername.setValue(null);
		binder.loadAll();
	}
	
	public void bloquearPantalla(String msg){
		Clients.showBusy(msg);
	}
	
	private void desbloquearPantalla(){
		Clients.clearBusy();
	}
	
	public void llenarCombosConUser(Cliente user){
		textboxUsername.setValue(user.getUsername());
		textboxEmail.setValue(user.getEmail());
	}


	public Window getUsuariosActualesWindow() {
		return usuariosActualesWindow;
	}
	public void setUsuariosActualesWindow(Window usuariosActualesWindow) {
		this.usuariosActualesWindow = usuariosActualesWindow;
	}
	
	
	
	
}

class GuardarUsuarioEventListener implements EventListener<Event>{

	AltaUsuarioComposer composer;
	
	public GuardarUsuarioEventListener(AltaUsuarioComposer composer){
		this.composer = composer;
	}
	
	public void onEvent(Event event) throws Exception {
		if(event.getName().equals(Events.ON_USER)){
			if(event.getData() instanceof Window){
				Window usuariosActualesWindow = (Window) (event.getData());
				composer.setUsuariosActualesWindow(usuariosActualesWindow);				
			}else{
				Map<String,Object> params = (Map<String,Object>) event.getData();
				if(params.get("accion").equals("editar")){
					composer.llenarCombosConUser((Cliente) params.get("usuario"));					
				}
				if(params.get("accion").equals("eliminar")){
					composer.limpiarCampos();
				}
			}
		}else{
			composer.guardar();			
		}
		
	}
	
}
