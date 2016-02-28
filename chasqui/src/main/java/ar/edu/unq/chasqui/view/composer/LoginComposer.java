package ar.edu.unq.chasqui.view.composer;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.Categoria;
import ar.edu.unq.chasqui.model.Fabricante;
import ar.edu.unq.chasqui.model.Imagen;
import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.model.Variante;
import ar.edu.unq.chasqui.services.impl.FileSaver;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;

@SuppressWarnings({ "serial", "deprecation" })
public class LoginComposer  extends GenericForwardComposer<Component>{

	
	
	private AnnotateDataBinder binder;
	private Textbox usernameLoggin;
	private Textbox passwordLoggin;
	private Label labelError;
	private Button logginButton;
	private Popup emailPopUp;
	private Toolbarbutton olvidoSuPassword;
	private Textbox emailTextbox;
	private Button cerrarPopUpButton;
	private Window loginWindow;

	
	private UsuarioService service;

	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		service = (UsuarioService) SpringUtil.getBean("usuarioService");
		comp.addEventListener(Events.ON_NOTIFY, new EnvioEmailListener(this));
		
		
	}
	
	
	public void onClick$logginButton(){
		String password = passwordLoggin.getValue();
		if (!password.matches("^[a-zA-Z0-9]*$") || password.length() < 8){
			labelError.setVisible(true);
			passwordLoggin.setValue("");
			usernameLoggin.setValue("");
			binder.loadAll();
			return;
		};
		Usuario user = new Usuario();
		user.setUsername(usernameLoggin.getValue());
		user.setKilometroSeleccionado(4);
		user.setMontoMinimoCompra(40);
		user.setPassword(password);
		user.setFechaProximaEntrega(new Date());
		user.setEmail("jfflores90@gmail");
		Imagen img = new Imagen();
		List<Categoria>categorias = new ArrayList<Categoria>();
		Categoria c = new Categoria();
		c.setNombre("Envasados");
		categorias.add(c);
		categorias.add(c);
		categorias.add(c);
		categorias.add(c);
		List<Producto>productos = new ArrayList<Producto>();
		Producto p = new Producto();
		p.setCategoria(c);
		p.setNombre("Aceite");
		p.setDescripcion("Esto es un aceite");
		Fabricante f = new Fabricante();
		f.setNombre("Fabricante");
		List<Fabricante>fss = new ArrayList<Fabricante>();
		fss.add(f);
		p.setFabricante(f);
		List<Caracteristica>cas = new ArrayList<Caracteristica>();
		Caracteristica ca = new Caracteristica();
		ca.setNombre("caracteristica");
		cas.add(ca);
		p.setCaracteristicas(cas);
		productos.add(p);
		productos.add(p);
		productos.add(p);
		productos.add(p);
		Variante v = new Variante();
		v.setStock(5);
		v.setPrecio(10);
		v.setNombre("Sarasa");
		List<Imagen>imagenes = new ArrayList<Imagen>();
		Imagen img2 = new Imagen();
		img2.setNombre("perfil.jpg");
		img2.setPath("/imagenes/usuarios/"+user.getUsername()+"/perfil.jpg");
		imagenes.add(img2);
		v.setImagenes(imagenes);
		List<Variante>h = new ArrayList<Variante>();
		h.add(v);
		p.setVariantes(h);
		user.setCategorias(categorias);
		user.setProductos(productos);
		user.setFabricantes(fss);
		
		img.setNombre("perfil.jpg");
		img.setPath("/imagenes/usuarios/"+user.getUsername()+"/perfil.jpg");
		user.setPathImagen(img.getPath());
		service.guardarUsuario(user);
		Executions.getCurrent().getSession().setAttribute(Constantes.SESSION_USERNAME, user);
		Executions.sendRedirect("administracion.zul");
		// validar Usuario y re enviar a la pagina de adm 
		// mandando por session al usuario 
		
	}
	
	public void onOlvidoPassword(){
		emailPopUp.open(olvidoSuPassword);
	}
	
	
	public void bloquearPantalla(String msg){
		Clients.showBusy(msg);
	}
	
	public void desbloquearPantalla(){
		Clients.clearBusy();
	}
	
	public void onClick$emailButton(){
		bloquearPantalla("Procesando...");
		Events.echoEvent(Events.ON_NOTIFY, loginWindow, null);
	}
	
	public void onEnviarEmail() {
		try{
			String email = emailTextbox.getValue();
			if(StringUtils.isEmpty(email) || !email.matches(Constantes.EMAIL_REGEX_PATTERN)){
				throw new WrongValueException(emailTextbox,"Por favor ingrese un email valido.");
			}
		}finally{
			desbloquearPantalla();						
		}
		
	}
	
	public void onClick$cerrarPopUpButton(){
		emailPopUp.close();
	}
	
	
	
	
	
	
	public Textbox getEmailTextbox() {
		return emailTextbox;
	}


	public void setEmailTextbox(Textbox emailTextbox) {
		this.emailTextbox = emailTextbox;
	}


	public Button getLogginButton() {
		return logginButton;
	}


	public void setLogginButton(Button logginButton) {
		this.logginButton = logginButton;
	
	}
	public Label getLabelError() {
		return labelError;
	}


	public void setLabelError(Label labelError) {
		this.labelError = labelError;
	}

	
	public Button getCerrarPopUpButton() {
		return cerrarPopUpButton;
	}


	public void setCerrarPopUpButton(Button cerrarPopUpButton) {
		this.cerrarPopUpButton = cerrarPopUpButton;
	}


	public Toolbarbutton getOlvidoSuPassword() {
		return olvidoSuPassword;
	}


	public void setOlvidoSuPassword(Toolbarbutton olvidoSuPassword) {
		this.olvidoSuPassword = olvidoSuPassword;
	}


	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public Textbox getUsernameLoggin() {
		return usernameLoggin;
	}

	public void setUsernameLoggin(Textbox usernameLoggin) {
		this.usernameLoggin = usernameLoggin;
	}

	public Textbox getPasswordLoggin() {
		return passwordLoggin;
	}

	public void setPasswordLoggin(Textbox passwordLoggin) {
		this.passwordLoggin = passwordLoggin;
	}


	public Popup getEmailPopUp() {
		return emailPopUp;
	}


	public void setEmailPopUp(Popup emailPopUp) {
		this.emailPopUp = emailPopUp;
	}
	
	
	
	
}
class EnvioEmailListener implements EventListener<Event>{
	
	LoginComposer composer;
	
	public EnvioEmailListener (LoginComposer l){
		composer = l;
	}
	
	public void onEvent(Event event) throws Exception {
		composer.onEnviarEmail();
		
	}
	
}
