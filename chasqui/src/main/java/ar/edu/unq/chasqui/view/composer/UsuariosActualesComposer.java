package ar.edu.unq.chasqui.view.composer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.view.renders.UsuarioRenderer;

@SuppressWarnings({"serial","deprecation"})
public class UsuariosActualesComposer extends GenericForwardComposer<Component> {
	
	private AnnotateDataBinder binder;
	private Listbox listboxUsuarios;
	private List<Cliente>usuarios;
	private Window altaUsuarioWindow;
	private Window administracionWindow;
	private Cliente usuarioSeleccionado;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		administracionWindow = (Window) findAdministracionWindow(comp);
		listboxUsuarios.setItemRenderer(new UsuarioRenderer((Window) this.self));
		conectarVentanas(administracionWindow);
		Cliente user = new Cliente();
		user.setUsername("EvilShun");
		user.setEmail("jfflores90@gmail.com");
		Events.sendEvent(Events.ON_USER,altaUsuarioWindow,this.self);
		comp.addEventListener(Events.ON_NOTIFY, new AccionEventListener(this));
		usuarios = new ArrayList<Cliente>();
		usuarios.add(user);
		binder.loadAll();
	}
	
	
	private Component findAdministracionWindow(Component comp) {
		if(comp.getParent() instanceof Window && comp.getParent().getId().equals("administracionWindow")){
			return comp.getParent();
		}
		return findAdministracionWindow(comp.getParent());
	}
	
	public Component conectarVentanas(Component c){
		for(Component child : c.getChildren()){
			if(child instanceof Window && child.getId().equals("altaUsuarioWindow")){
				altaUsuarioWindow = (Window) child;
			}else{
				conectarVentanas(child);
			}
		}
		return null;
	}
	
	public void editar(Cliente u){
		Map<String,Object>params = new HashMap<String,Object>();
		params.put("accion", "editar");
		params.put("usuario", u);
		Events.sendEvent(Events.ON_USER,altaUsuarioWindow,params);
	}
	
	public void eliminar(final Cliente u){
		
		Messagebox.show(Labels.getLabel("zk.message.eliminar.usuario",new String[]{u.getUsername()}),
				Labels.getLabel("zk.tittle.eliminar.usuario"), Messagebox.YES | Messagebox.NO,Messagebox.QUESTION, new EventListener<Event>() {
					public void onEvent(Event event) throws Exception {
						switch ((Integer) event.getData()){
							case Messagebox.YES:
								Map<String,Object>params = new HashMap<String,Object>();
								params.put("accion", "eliminar");
								params.put("usuario", u);
								Events.sendEvent(Events.ON_USER,altaUsuarioWindow,params);
								// eliminar usuario
								usuarios.remove(u);
								binder.loadAll();		
							
							case Messagebox.NO:
								return;
							
						}
					}	
				});
	}
	
	
	
	

	public List<Cliente> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Cliente> usuarios) {
		this.usuarios = usuarios;
	}
	public Cliente getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}
	public void setUsuarioSeleccionado(Cliente usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

}

class AccionEventListener implements EventListener<Event>{
	
	UsuariosActualesComposer composer;
	public AccionEventListener(UsuariosActualesComposer c){
		this.composer = c;
	}
	public void onEvent(Event event) throws Exception {
		if(event.getName().equals(Events.ON_NOTIFY)){
			@SuppressWarnings("unchecked")
			Map<String,Object>param = (Map<String,Object>)event.getData();
			if(param.get("accion").equals("editar")){
				composer.editar((Cliente)param.get("usuario"));
			}
			if(param.get("accion").equals("eliminar")){
				composer.eliminar((Cliente) param.get("usuario"));
			}
		}
		
	}
	
}
