package ar.edu.unq.chasqui.view.composer;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import ar.edu.unq.chasqui.model.Categoria;
import ar.edu.unq.chasqui.model.Fabricante;
import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.view.genericEvents.Refresher;
import ar.edu.unq.chasqui.view.renders.CategoriaRenderer;
import ar.edu.unq.chasqui.view.renders.ProductoRenderer;
import ar.edu.unq.chasqui.view.renders.ProductorRenderer;

@SuppressWarnings({ "serial", "deprecation" })
public class AdministracionComposer extends GenericForwardComposer<Component> implements Refresher{
	
	private Window administracionWindow;
	private AnnotateDataBinder binder;
	private Radio radioCategorias;
	private Radio radioProductos;
	private Radio radioConfiguracion;
	private Radio radioAltaUsuario;
	private Radio radioProductores;
	private Categoria categoriaSeleccionada;
	private Toolbarbutton agregarButton;
	private Toolbarbutton agregarProductoButton;
	private Toolbarbutton agregarProductorButton;
	private Producto productoSeleccionado;
	private Listbox listboxProductos;
	private Listbox listboxProductores;
	private Listbox listboxCategorias;
	private Include configuracionInclude;
	private Include altaUsuarioInclude;
	private Include usuariosActualesInclude;
	private Vendedor usuarioLogueado;
	private Div divCategoria;
	private Div divProducto;
	private Div divProductores;
	
	public void doAfterCompose(Component comp) throws Exception{
		usuarioLogueado = (Vendedor) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME);
		if(usuarioLogueado == null){
			Executions.sendRedirect("/");
			return;
		}
		
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		if(usuarioLogueado instanceof Usuario){
			inicializacionUsuarioROOT();
		}else{
			inicializacionUsuarioAdministrador();		
		}
		comp.addEventListener(Events.ON_USER, new CategoriaEventListener(this));
		comp.addEventListener(Events.ON_NOTIFY, new ProductoEventListener(this));
		comp.addEventListener(Events.ON_RENDER, new RefreshEventListener(this));
		binder.loadAll();
	}

	public void inicializacionUsuarioROOT(){
		radioAltaUsuario.setChecked(true);
		radioCategorias.setDisabled(true);
		radioProductos.setDisabled(true);
		radioProductores.setDisabled(true);
		listboxCategorias.setVisible(false);
		radioConfiguracion.setDisabled(true);
		administracionWindow.setVisible(false);
		onClick$radioAltaUsuario();
	}
	
	public void inicializacionUsuarioAdministrador(){
		listboxProductos.setItemRenderer(new ProductoRenderer(this.self));
		listboxCategorias.setItemRenderer(new CategoriaRenderer(this.self));
		listboxProductores.setItemRenderer(new ProductorRenderer(this.self));
		administracionWindow.setVisible(true);
		radioCategorias.setChecked(true);
		onClick$radioCategorias();			
	}
	
	public void onClick$radioCategorias(){
		divProducto.setVisible(false);
		agregarProductoButton.setVisible(false);
		agregarProductorButton.setVisible(false);
		configuracionInclude.setVisible(false);
		divProductores.setVisible(false);
		altaUsuarioInclude.setVisible(false);
		usuariosActualesInclude.setVisible(false);
		agregarButton.setVisible(true);
		divCategoria.setVisible(true);
		binder.loadAll();
	}
	
	public void onClick$radioProductos(){
		agregarButton.setVisible(false);
		agregarProductorButton.setVisible(false);
		divCategoria.setVisible(false);
		configuracionInclude.setVisible(false);
		altaUsuarioInclude.setVisible(false);
		usuariosActualesInclude.setVisible(false);
		divProductores.setVisible(false);
		agregarProductoButton.setVisible(true);
		divProducto.setVisible(true);
		binder.loadAll();
	}
	
	public void onClick$radioConfiguracion(){
		agregarProductorButton.setVisible(false);
		agregarProductorButton.setVisible(false);
		agregarButton.setVisible(false);
		agregarProductoButton.setVisible(false);
		divCategoria.setVisible(false);
		divProducto.setVisible(false);
		altaUsuarioInclude.setVisible(false);
		usuariosActualesInclude.setVisible(false);
		divProductores.setVisible(false);
		configuracionInclude.setVisible(true);
		binder.loadAll();
	}
	
	public void onClick$radioAltaUsuario(){
		divProducto.setVisible(false);
		divCategoria.setVisible(false);
		agregarButton.setVisible(false);
		agregarProductoButton.setVisible(false);
		agregarProductorButton.setVisible(false);
		configuracionInclude.setVisible(false);
		divProductores.setVisible(false);
		altaUsuarioInclude.setVisible(true);
		usuariosActualesInclude.setVisible(true);
		binder.loadAll();
	}
	
	public void onClick$radioProductores(){
		divProducto.setVisible(false);
		divCategoria.setVisible(false);
		agregarButton.setVisible(false);
		agregarProductoButton.setVisible(false);
		configuracionInclude.setVisible(false);
		altaUsuarioInclude.setVisible(false);
		usuariosActualesInclude.setVisible(false);
		divProductores.setVisible(true);
		agregarProductorButton.setVisible(true);
		binder.loadAll();
	}
	
	public void refresh(){
		binder.loadAll();
	}
	
	public void onEditarCategoria(Categoria c){
		Map<String,Object>params = new HashMap<String,Object>();
		params.put("categoria",c);
		Window abmCategoria = (Window) Executions.createComponents("/abmCategoria.zul", this.self,params );
		abmCategoria.doModal();
	}
	
	public void onCrearCategoria(){
		Window abmCategoria = (Window) Executions.createComponents("/abmCategoria.zul", this.self,null);
		abmCategoria.doModal();
	}
	
	public void onEliminarCategoria(Categoria c){
		validarCategoriaValidaParaEliminar(c);
	}
	
	private void validarCategoriaValidaParaEliminar(Categoria c){
		if(c.getProductos().size() > 0){
				Messagebox.show("La categoria: '" + c.getNombre() + "' aún tiene productos asociados a ella. desasocie los mismos para eliminar la categoria"
						, "Error!", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		// eliminar Categoria
	}
	
	
	public void onEditarProducto(Producto p){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("accion", Constantes.VENTANA_MODO_EDICION);
		params.put("producto", p);
		Window windowProducto = (Window) Executions.createComponents("/abmProducto.zul", this.self, params);
		windowProducto.doModal();
	}
	
	public void onEliminarProducto(Producto p){
		// mostrar cartel
		p.getCategoria().eliminarProducto(p);
		alert("El producto: '" + p.getNombre() + "' fue eliminado con exito!");
	}
	
	public void onVisualizarProducto(Producto p){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("accion", Constantes.VENTANA_MODO_LECTURA);
		params.put("producto", p);
		Window windowProducto = (Window) Executions.createComponents("/abmProducto.zul", this.self, params);
		windowProducto.doModal();
	}
	
	public void onCrearProducto(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("accion", Constantes.VENTANA_MODO_EDICION);
		Window windowProducto = (Window) Executions.createComponents("/abmProducto.zul", this.self, params);
		windowProducto.doModal();
	}
	
	public void onCrearProductor(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("accion", Constantes.VENTANA_MODO_EDICION);
		Window windowProducto = (Window) Executions.createComponents("/abmProductor.zul", this.self, params);
		windowProducto.doModal();
	}
	
	public void verProductor(Fabricante f){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("accion", Constantes.VENTANA_MODO_LECTURA);
		params.put("productor", f);
		Window windowProducto = (Window) Executions.createComponents("/abmProductor.zul", this.self, params);
		windowProducto.doModal();
	}
	
	public void eliminarProductor(Fabricante f){
		// mostrar cartel
		usuarioLogueado.eliminarProductor(f);
		alert("El productor: '" + f.getNombre() + "' fue eliminado con exito!");
		this.binder.loadAll();
	}
	
	

	public Vendedor getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(Vendedor usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public Categoria getCategoriaSeleccionada() {
		return categoriaSeleccionada;
	}
	public void setCategoriaSeleccionada(Categoria categoriaSeleccionada) {
		this.categoriaSeleccionada = categoriaSeleccionada;
	}

	public Producto getProductoSeleccionado() {
		return productoSeleccionado;
	}
	public void setProductoSeleccionado(Producto productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

}


class CategoriaEventListener implements EventListener<Event>{

	AdministracionComposer composer;
	public CategoriaEventListener(AdministracionComposer comp){
		this.composer = comp;
	}
	
	public void onEvent(Event event) throws Exception {
		Map<String,Object>params = (Map<String,Object>) event.getData();
		if(params.get("accion").equals("editar")){
			this.composer.onEditarCategoria((Categoria) params.get("categoria"));
		}
		if(params.get("accion").equals("eliminar")){
			this.composer.onEliminarCategoria((Categoria) params.get("categoria"));
		}
		
	}
	
}


class ProductoEventListener implements EventListener<Event>{

	AdministracionComposer composer;
	public ProductoEventListener(AdministracionComposer comp){
		this.composer = comp;
	}
	
	public void onEvent(Event event) throws Exception {
		Map<String,Object>params = (Map<String,Object>) event.getData();
		Producto p = (Producto) params.get("producto");
		Fabricante f = (Fabricante) params.get("productor");
		if(params.get("accion").equals("editar") && p != null){
			this.composer.onEditarProducto(p);
		}
		if(params.get("accion").equals("eliminar") && p != null){
			this.composer.onEliminarProducto(p);
		}
		if(params.get("accion").equals("visualizar") && p != null){
			this.composer.onVisualizarProducto(p);
		}
		if(params.get("accion").equals("eliminar") && f != null){
			this.composer.eliminarProductor(f);
		}
		if(params.get("accion").equals("visualizar") && f != null){
			this.composer.verProductor(f);
		}
		
	}
	
}

class RefreshEventListener implements EventListener<Event>{

	AdministracionComposer composer;
	public RefreshEventListener(AdministracionComposer adm){
		this.composer = adm;
	}

	public void onEvent(Event event) throws Exception {
		if(event.getData() != null){
			if(event.getData() instanceof Categoria){
				Categoria c = (Categoria) event.getData();
				composer.getUsuarioLogueado().getCategorias().add(c);				
			}
			if(event.getData() instanceof Producto){
				Producto p= (Producto) event.getData();
				p.getCategoria().agregarProducto(p);
			}
		}
		composer.refresh();
		
	}
	
}