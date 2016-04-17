package ar.edu.unq.chasqui.view.composer;


import java.util.ArrayList;
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
import org.zkoss.zkplus.spring.SpringUtil;
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
import ar.edu.unq.chasqui.model.Variante;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.services.interfaces.CaracteristicaService;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;
import ar.edu.unq.chasqui.view.genericEvents.RefreshListener;
import ar.edu.unq.chasqui.view.genericEvents.Refresher;

@SuppressWarnings({"serial","deprecation"})
public class ABMProductoComposer extends GenericForwardComposer<Component> implements Refresher{

	private AnnotateDataBinder binder;
	private Textbox nombreProducto;
	private Combobox comboCategorias;
	private Combobox comboFabricantes;
	private Combobox comboCaracteristicas;
	private Toolbarbutton botonAgregarCaracteristica;
	private Toolbarbutton botonAgregarFabricante;
	private Toolbarbutton botonAgregarCategoria;
	private Listbox listboxCaracteristicas;
	private Listbox listboxVariante;
	private Toolbarbutton botonGuardar;
	private Toolbarbutton botonCancelar;
	private Button botonAgregarVariante;
	private Textbox agregarCaractTextbox;
	private Popup popUpCaracteristica;
	private Producto model;
	private List<Caracteristica> caracteristicas;
	private Categoria categoriaSeleccionada;
	private Caracteristica caracteristicaSeleccionada;
	private Variante varianteSeleccionada;
	private Fabricante productorSeleccionado;
	private List<Variante>varianteRollback;
	private List<Caracteristica>caracteristicasProducto;
	private Caracteristica caracteristicaProductoSeleccionada;
	private Vendedor usuario;
	private boolean modoEdicion;
	
	
	private UsuarioService usuarioService;
	private CaracteristicaService caracteristicaService;
	
	
	
	
	
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		model = (Producto) Executions.getCurrent().getArg().get("producto");
		Integer accion = (Integer) Executions.getCurrent().getArg().get("accion");
		usuario = (Vendedor) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME);
		usuarioService = (UsuarioService) SpringUtil.getBean("usuarioService");
		caracteristicaService = (CaracteristicaService) SpringUtil.getBean("caracteristicaService");
		caracteristicasProducto = caracteristicaService.buscarCaracteristicasProductoBy(usuario.getId());
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
		comboCaracteristicas.setDisabled(true);
		botonAgregarFabricante.setDisabled(true);
		botonAgregarCategoria.setDisabled(true);
		botonGuardar.setDisabled(true);
		listboxCaracteristicas.setDisabled(true);
		botonAgregarVariante.setDisabled(true);
	}
	
	public void inicializarModoEdicion(){
		modoEdicion= true;
		if(model.getCategoria() != null && model.getFabricante() != null){
			categoriaSeleccionada = model.getCategoria();
			productorSeleccionado = model.getFabricante();			
		}
		caracteristicas = model.getCaracteristicas();
		nombreProducto.setValue(model.getNombre());
		varianteRollback = new ArrayList<Variante>( model.getVariantes());
	}

	
	public void onClick$botonAgregarCategoria(){
		Window w = (Window) Executions.createComponents("/abmCategoria.zul", this.self, null);
		w.doModal();		
	}
	
	public void onClick$botonGuardar(){
		validaciones();
		model.setNombre(nombreProducto.getValue());
		model.setCaracteristicas(caracteristicas);
		categoriaSeleccionada.agregarProducto(model);	
		model.setCategoria(categoriaSeleccionada);
		model.setFabricante(productorSeleccionado);
		productorSeleccionado.agregarProducto(model);
		usuarioService.guardarUsuario(usuario);
		Events.sendEvent(Events.ON_NOTIFY, this.self.getParent(), null);
		this.self.detach();
	}
	
	private void validaciones(){
		String nombre = nombreProducto.getValue();
		if(StringUtils.isEmpty(nombre)){
			throw new WrongValueException(nombreProducto,"El nombre no debe ser vacio!");
		}
		if(categoriaSeleccionada == null){
			throw new WrongValueException(comboCategorias,"Se debe seleccionar una categoria");
		}
		if(productorSeleccionado == null){
			throw new WrongValueException(comboFabricantes,"Se debe seleccionar un productor");
		}
		if(caracteristicas == null || caracteristicas.isEmpty()){
			throw new WrongValueException(listboxCaracteristicas,"Se debe agregar al menos una Caracteristica");
		}
		if(model.getVariantes() == null || model.getVariantes().isEmpty()){
			throw new WrongValueException(listboxVariante,"Debe agregar al menos una varidad del producto");
		}
	}
	
	public void onEliminarVariante(){
		model.getVariantes().remove(varianteSeleccionada);
		this.binder.loadAll();
	}
	
	public void onClick$botonCancelar(){
		rollbackProducto();
		this.self.detach();
	}
	
	public void onClose$productosWindow(){
		rollbackProducto();
		this.self.detach();
	}
	
	private void rollbackProducto(){
		model.setVariantes(varianteRollback);
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
		
		if(caracteristicaProductoSeleccionada == null){
			throw new WrongValueException(comboCaracteristicas,"Debe seleccionar una caracteristica.");
		}
		if(caracteristicas.contains(caracteristicaProductoSeleccionada)){
			throw new WrongValueException("El producto ya posee la caracteristica que desea agregar");			
		}
		caracteristicas.add(caracteristicaProductoSeleccionada);
		comboCaracteristicas.setValue(null);
		caracteristicaSeleccionada = null;
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

	





	public List<Caracteristica> getCaracteristicasProducto() {
		return caracteristicasProducto;
	}



	public void setCaracteristicasProducto(List<Caracteristica> caracteristicasProducto) {
		this.caracteristicasProducto = caracteristicasProducto;
	}



	public Caracteristica getCaracteristicaProductoSeleccionada() {
		return caracteristicaProductoSeleccionada;
	}



	public void setCaracteristicaProductoSeleccionada(Caracteristica caracteristicaProductoSeleccionada) {
		this.caracteristicaProductoSeleccionada = caracteristicaProductoSeleccionada;
	}



	public Fabricante getProductorSeleccionado() {
		return productorSeleccionado;
	}



	public void setProductorSeleccionado(Fabricante productorSeleccionado) {
		this.productorSeleccionado = productorSeleccionado;
	}



	public List<Caracteristica> getCaracteristicas() {
		return caracteristicas;
	}
	public void setCaracteristicas(List<Caracteristica> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	public Vendedor getUsuario() {
		return usuario;
	}

	public void setUsuario(Vendedor usuario) {
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
