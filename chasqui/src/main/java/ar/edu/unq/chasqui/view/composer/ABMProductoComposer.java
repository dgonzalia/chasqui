package ar.edu.unq.chasqui.view.composer;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.common.util.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.Categoria;
import ar.edu.unq.chasqui.model.Fabricante;
import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.model.Variante;
import ar.edu.unq.chasqui.view.genericEvents.RefreshListener;
import ar.edu.unq.chasqui.view.genericEvents.Refresher;

@SuppressWarnings({"serial","deprecation"})
public class ABMProductoComposer extends GenericForwardComposer<Component> implements Refresher{

	private AnnotateDataBinder binder;
	private Textbox nombreProducto;
	private Combobox comboCategorias;
	private Combobox comboFabricantes;
	private Toolbarbutton botonAgregarCaracteristica;
	private Toolbarbutton botonAgregarFabricante;
	private Toolbarbutton botonAgregarCategoria;
	private Listbox listboxCaracteristicas;
	private Toolbarbutton botonGuardar;
	private Toolbarbutton botonCancelar;
	private Button botonAgregarVariante;
	private Textbox agregarCaractTextbox;
//	private Button agregarCaractButton;
	private Popup popUpCaracteristica;
	private Producto model;
	private List<Caracteristica> caracteristicas;
	private Categoria categoriaSeleccionada;
	private Caracteristica caracteristicaSeleccionada;
	private Variante varianteSeleccionada;
	private Fabricante fabricanteSeleccionado;
	private Usuario usuario;
	private boolean modoEdicion;
	
	
	
	
	
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		model = (Producto) Executions.getCurrent().getArg().get("producto");
		Integer accion = (Integer) Executions.getCurrent().getArg().get("accion");
		usuario = (Usuario) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME);
		comp.addEventListener(Events.ON_RENDER, new RefreshListener<ABMProductoComposer>(this));
		inicializarVentana(accion);	
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}

	

	public void refresh() {
		binder.loadAll();
	}
	
	public void inicializarVentana(Integer accion){
	
		if(model != null && Constantes.VENTANA_MODO_EDICION.equals(accion)){
			inicializarModoEdicion();
		}
		
		if(Constantes.VENTANA_MODO_EDICION.equals(accion) && model == null){
			model = Producto.crearProductoEmpty();
			inicializarModoEdicion();
		}
		
		if(Constantes.VENTANA_MODO_LECTURA.equals(accion)){
			inicializarModoLectura();
		}
		
	}
	
	public void inicializarModoLectura(){
		inicializarModoEdicion();
		modoEdicion = false;
		nombreProducto.setDisabled(true);
		comboCategorias.setDisabled(true);
		comboFabricantes.setDisabled(true);
		botonAgregarCaracteristica.setDisabled(true);
		botonAgregarFabricante.setDisabled(true);
		botonAgregarCategoria.setDisabled(true);
		botonGuardar.setDisabled(true);
		listboxCaracteristicas.setDisabled(true);
		botonAgregarVariante.setDisabled(true);
//		intboxPrecio.setDisabled(true);
//		ckEditor.setCustomConfigurationsPath("/js/ckEditorReadOnly.js");
	}
	
	public void inicializarModoEdicion(){
		modoEdicion= true;
		if(model.getCategoria() != null && model.getFabricante() != null){
			comboCategorias.setValue(model.getCategoria().getNombre());
			comboFabricantes.setValue(model.getFabricante().getNombre());			
		}
		caracteristicas = model.getCaracteristicas();
		nombreProducto.setValue(model.getNombre());
	}

	
	public void onClick$botonAgregarCategoria(){
		Window w = (Window) Executions.createComponents("/abmCategoria.zul", this.self, null);
		w.doModal();		
	}
	
	public void onClick$botonGuardar(){
		// guardar en DB
		//validar los datos
		
		
	}
	
	public void onClick$botonCancelar(){
		this.self.detach();
	}
	
	
	
	public void onVerVariante(){
		Map<String,Object>params = new HashMap<String,Object>();
		params.put("producto",model);
		params.put("variante", varianteSeleccionada);
		Window w = (Window) Executions.createComponents("/abmVariante.zul", this.self, params);
		w.doModal();
	}
	
	public void onAltaVariante(){
		Map<String,Object>params = new HashMap<String,Object>();
		params.put("producto",model);
		Window w = (Window) Executions.createComponents("/abmVariante.zul", this.self, params);
		w.doModal();
	}
	
	public void onClick$botonAgregarFabricante(){
		Window w = (Window)Executions.createComponents("/abmProductor.zul", this.self, null);
		w.doModal();
	}
	
	public void onClick$botonAgregarCaracteristica(){
		popUpCaracteristica.open(botonAgregarCaracteristica);
	}
	
	public void onClick$agregarCaractButton(){
		String caract = agregarCaractTextbox.getValue();
		if(StringUtils.isEmpty(caract)){
			throw new WrongValueException(agregarCaractTextbox,"La caracteristica no debe ser vacia!");
		}
		Caracteristica c = new Caracteristica(caract);
		if(caracteristicas.contains(c)){
			throw new WrongValueException("El producto ya posee la caracteristica que desea agregar");
		}
		model.getCaracteristicas().add(c);
		refresh();
	}
	
	public void onEliminarCaracteristica(){
		if(!listboxCaracteristicas.isDisabled()){			
			caracteristicas.remove(caracteristicaSeleccionada);
		}
		refresh();
	}
	
	
	
	
	public Producto getModel() {
		return model;
	}
	public void setModel(Producto model) {
		this.model = model;
	}

	public Categoria getCategoriaSeleccionada() {
		return categoriaSeleccionada;
	}

	public void setCategoriaSeleccionada(Categoria categoriaSeleccionada) {
		this.categoriaSeleccionada = categoriaSeleccionada;
	}

	public Caracteristica getCaracteristicaSeleccionada() {
		return caracteristicaSeleccionada;
	}

	public void setCaracteristicaSeleccionada(Caracteristica caracteristicaSeleccionada) {
		this.caracteristicaSeleccionada = caracteristicaSeleccionada;
	}

	

	public Fabricante getFabricanteSeleccionado() {
		return fabricanteSeleccionado;
	}

	public void setFabricanteSeleccionado(Fabricante fabricanteSeleccionado) {
		this.fabricanteSeleccionado = fabricanteSeleccionado;
	}
	public List<Caracteristica> getCaracteristicas() {
		return caracteristicas;
	}
	public void setCaracteristicas(List<Caracteristica> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isModoEdicion() {
		return modoEdicion;
	}

	public void setModoEdicion(boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}
	public Variante getVarianteSeleccionada() {
		return varianteSeleccionada;
	}
	public void setVarianteSeleccionada(Variante varianteSeleccionada) {
		this.varianteSeleccionada = varianteSeleccionada;
	}
	
	
	
	
}
