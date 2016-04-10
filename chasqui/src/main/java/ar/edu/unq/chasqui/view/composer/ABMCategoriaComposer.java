package ar.edu.unq.chasqui.view.composer;

import org.apache.cxf.common.util.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import ar.edu.unq.chasqui.model.Categoria;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;

@SuppressWarnings({"serial","deprecation"})
public class ABMCategoriaComposer extends GenericForwardComposer<Component> {

	private Categoria model;
	private Toolbarbutton buttonGuardar;
	private Textbox textboxNombreCategoria;
	private AnnotateDataBinder binder;
	private Vendedor usuario;
	private UsuarioService usuarioService;
	
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		model = (Categoria) Executions.getCurrent().getArg().get("categoria");
		usuarioService = (UsuarioService) SpringUtil.getBean("usuarioService");
		usuario = (Vendedor) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME);
		if(model != null){
			iniciarModoEdicion();
		}
		binder.loadAll();
		
	}
	
	
	private void iniciarModoEdicion(){
		textboxNombreCategoria.setValue(model.getNombre());
	}
	
	
	public void onClick$buttonGuardar(){
		if(StringUtils.isEmpty(textboxNombreCategoria.getValue())){
			throw new WrongValueException("El nombre no debe ser vacio!");
		}
		
		// guardar y cerrar
		if(model != null){
			model.setNombre(textboxNombreCategoria.getValue());
			usuarioService.guardarUsuario(usuario);
			Events.sendEvent(Events.ON_RENDER,this.self.getParent(),null);
		}else{
			model = new Categoria();
			model.setNombre(textboxNombreCategoria.getValue());
			usuario.agregarCategoria(model);
//			model.setProductos(null);
			usuarioService.guardarUsuario(usuario);
			Events.sendEvent(Events.ON_RENDER,this.self.getParent(),null);
		}
		// GUARDAR EN DB
		this.self.detach();
	}
}
