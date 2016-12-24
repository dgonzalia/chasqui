package ar.edu.unq.chasqui.services.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.exceptions.UsuarioExistenteException;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Direccion;
import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.model.ProductoPedido;
import ar.edu.unq.chasqui.security.Encrypter;
import ar.edu.unq.chasqui.security.PasswordGenerator;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;
import freemarker.template.Configuration;
import freemarker.template.SimpleObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class MailService {

	@Autowired
	private JavaMailSender mailSender;	
	@Autowired
	private UsuarioService usuarioService;	
	@Autowired
	private Encrypter encrypter;
	@Autowired
	private PasswordGenerator passwordGenerator;
	
	public static final Logger logger = Logger.getLogger(MailService.class);
	
	private Template obtenerTemplate(String nombreTemplate) throws IOException{
		Configuration c = new Configuration();
		c.setObjectWrapper(new SimpleObjectWrapper());
		c.setClassForTemplateLoading(MailService.class, "/templates/mail/");
		return c.getTemplate(nombreTemplate);
	}
	
	
	
	public void enviarEmailBienvenidaVendedor(String destino,String usuario,String password) throws IOException, MessagingException, TemplateException{
		Template t = this.obtenerTemplate("emailBienvenida.ftl"); 
		MimeMessage m = mailSender.createMimeMessage();
		m.setSubject(MimeUtility.encodeText("Bienvenido "+ usuario + " a Chasqui","UTF-8","B"));
		MimeMessageHelper helper = new MimeMessageHelper(m,true,"UTF-8");
		StringWriter writer = new StringWriter();
		ClassPathResource resource = new ClassPathResource("templates/imagenes/chasqui.png");
		helper.setFrom("administrator-chasqui-noreply@chasqui.org");
		helper.setTo(destino);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("nombreUsuario", usuario);
		params.put("passwordUsuario", password);
		t.process(params, writer);
		
		writer.flush();
		writer.close();
		helper.setText(writer.toString(),true);
		helper.addInline("logochasqui", resource);
		mailSender.send(m);		
	}
	
	
	
	public void enviarEmailNotificacionPedido(String destino,String cuerpoEmail,String nombreUsuario, String apellidoUsuario) throws IOException, MessagingException, TemplateException{
		Template t = this.obtenerTemplate("emailNotificacionPedido.ftl"); 
		MimeMessage m = mailSender.createMimeMessage();
		m.setSubject(MimeUtility.encodeText("Tu Pedido esta a punto de vencer","UTF-8","B"));
		MimeMessageHelper helper = new MimeMessageHelper(m,true,"UTF-8");
		StringWriter writer = new StringWriter();
		ClassPathResource resource = new ClassPathResource("templates/imagenes/chasqui.png");
		helper.setFrom("administrator-chasqui-noreply@chasqui.org");
		helper.setTo(destino);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("cuerpo", cuerpoEmail);
		params.put("nombre", nombreUsuario);
		params.put("apellido", apellidoUsuario);
		t.process(params, writer);
		
		writer.flush();
		writer.close();
		helper.setText(writer.toString(),true);
		helper.addInline("logochasqui", resource);
		mailSender.send(m);		
	}
	
	
	@Transactional
	public void enviarEmailRecuperoContraseña(String destino, String usuario) throws Exception{
		
		Template t = this.obtenerTemplate("emailRecupero.ftl");
		MimeMessage m = mailSender.createMimeMessage();
		m.setSubject(MimeUtility.encodeText("Aviso de Recupero de contraseña ","UTF-8","B"));
		MimeMessageHelper helper = new MimeMessageHelper(m,true,"UTF-8");
		StringWriter writer = new StringWriter();
		ClassPathResource resource = new ClassPathResource("templates/imagenes/chasqui.png");
		helper.setFrom("administrator-chasqui-noreply@chasqui.org");
		helper.setTo(destino);
		
		String password = passwordGenerator.generateRandomToken(); 
		usuarioService.modificarPasswordUsuario(destino, encrypter.encrypt(password));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("nombreUsuario", usuario);
		params.put("passwordUsuario", password);
		t.process(params, writer);
		
		writer.flush();
		writer.close();
		helper.setText(writer.toString(),true);
		helper.addInline("logochasqui", resource);
		mailSender.send(m);		
		
	}


	@Transactional
	public void enviarEmailRecuperoContraseñaCliente(String email) throws Exception {
		if (usuarioService.existeUsuarioCon(email)) {
			Cliente c = (Cliente) usuarioService.obtenerUsuarioPorEmail(email);
			this.enviarEmailRecuperoContraseña(email, c.getNickName());
		}
		else {
			throw new UsuarioExistenteException() ;
		}
	}



	public void enviarEmailDeInvitacionChasqui(Cliente clienteOrigen, String destino) throws Exception {
		Template t = this.obtenerTemplate("emailInvitacion.ftl"); 
		MimeMessage m = mailSender.createMimeMessage();
		m.setSubject(MimeUtility.encodeText("Conocés Chasqui??","UTF-8","B"));
		MimeMessageHelper helper = new MimeMessageHelper(m,true,"UTF-8");
		StringWriter writer = new StringWriter();
		ClassPathResource resource = new ClassPathResource("templates/imagenes/chasqui.png");
		helper.setFrom("administrator-chasqui-noreply@chasqui.org");
		helper.setTo(destino);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("usuarioOrigen", clienteOrigen.getNickName());
		params.put("mailOrigen",clienteOrigen.getEmail());
		params.put("vendedor", "Puente Del Sur");
		t.process(params, writer);
		
		writer.flush();
		writer.close();
		helper.setText(writer.toString(),true);
		helper.addInline("logochasqui", resource);
		mailSender.send(m);		
	}
	
	
	
	public void enviarEmailConfirmacionPedido(final String emailVendedor,final String emailCliente, final Pedido p){
		
		
		new Thread(){
			
			public void run(){
				
				String tablaContenidoPedido = armarTablaContenidoDePedido(p);
				String tablaDireccionEntrega = armarTablaDireccionDeEntrega(p.getDireccionEntrega());
				String cuerpoCliente = armarCuerpoCliente();
				String cuerpoVendedor = armarCuerpoVendedor(emailCliente);
				
				try{
					Template template = obtenerTemplate("emailConfirmacionPedido.ftl");
					MimeMessage m = mailSender.createMimeMessage();
					m.setSubject(MimeUtility.encodeText("Confirmación de Compra","UTF-8","B"));
					MimeMessageHelper helper = new MimeMessageHelper(m,true,"UTF-8");
					StringWriter writer = new StringWriter();
					ClassPathResource resource = new ClassPathResource("templates/imagenes/chasqui.png");
					helper.setFrom("administrator-chasqui-noreply@chasqui.org");
					helper.setTo(emailCliente);
					
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("cuerpo", cuerpoCliente);
					params.put("tablaContenidoPedido",tablaContenidoPedido);
					params.put("tablaDireccionDeEntrega", tablaDireccionEntrega);
					params.put("agradecimiento","Muchas gracias por utilizar el sistema Chasqui");
					template.process(params, writer);
					
					writer.flush();
					writer.close();
					helper.setText(writer.toString(),true);
					helper.addInline("logochasqui", resource);
					mailSender.send(m);					
					
					Map<String,Object> paramsVendedor = new HashMap<String,Object>();
					paramsVendedor.put("cuerpo", cuerpoVendedor);
					paramsVendedor.put("tablaContenidoPedido",tablaContenidoPedido);
					paramsVendedor.put("tablaDireccionDeEntrega", tablaDireccionEntrega);
					paramsVendedor.put("agradecimiento","Muchas gracias por utilizar el sistema Chasqui");

					Template templateVendedor = obtenerTemplate("emailConfirmacionPedido.ftl");
					StringWriter writerVendedor = new StringWriter();
					MimeMessage mVendedor = mailSender.createMimeMessage();
					mVendedor.setSubject(MimeUtility.encodeText("Confirmación de Compra","UTF-8","B"));
					MimeMessageHelper helperVendedor = new MimeMessageHelper(mVendedor,true,"UTF-8");
					helperVendedor.setFrom("administrator-chasqui-noreply@chasqui.org");
					helperVendedor.setTo(emailVendedor);
					templateVendedor.process(paramsVendedor, writerVendedor);
					writerVendedor.flush();
					writerVendedor.close();
					helperVendedor.setText(writerVendedor.toString(),true);
					helperVendedor.addInline("logochasqui", resource);
					mailSender.send(mVendedor);
				}catch(Exception e){
					logger.error(e);;
				}
				
			}
			
			
		}.start();
		
	}
	



	
	private String armarCuerpoCliente(){
		return "Datos de confirmación de compra";
	}

	
	private String armarCuerpoVendedor(String usuario){
		return "El usuario: "+ usuario +" ha confirmado su compra (Los detalles del mismo se encuentran debajo y también pueden visualizarse en el panel de administración)";
	}
	
	
	
	
	private String armarTablaDireccionDeEntrega(Direccion d){
		String departamento =  d.getDepartamento() != null ? d.getDepartamento() : "---";
		String tabla = "<table border="+ "0" +">"
				+ "<tr><td>Calle:</td><td>" + d.getCalle() + "</td></tr>"
				+ "<tr><td>Altura:</td>" + d.getAltura() + "</td></tr>"
				+ "<tr><td>Departamento:</td>" + departamento + "</td></tr>"
				+ "<tr><td>Cod. posta:</td>" + d.getCodigoPostal() + "</td></tr>"
				+ "<tr><td>Localidad:</td>" + d.getLocalidad() + "</td></tr>";
		
		return tabla;
		
	}
	

	private String armarTablaContenidoDePedido(Pedido p) {
		String tabla = armarHeader();
		String footer = armarFooter(p.getMontoActual());
		for(ProductoPedido pp : p.getProductosEnPedido()){
			tabla += armarFilaDetalleProducto(pp);
		}		
		tabla += footer;
		return tabla;
	}

	
	
	private String armarFilaDetalleProducto(ProductoPedido pp){
		return  "<tr><td>"+ pp.getNombreProducto() + pp.getNombreVariante() + "</td><td>" +pp.getPrecio()+ "</td><td> "+ pp.getCantidad() +"</td></tr>";
	}	

	private String armarHeader() {
		return "<table border="+ "1" +"><tr><th>Producto</th><th>Precio por Unidad</th><th>Cantidad</th></tr>";
	}
	
	private String armarFooter(Double total){
		return "<tr><td colspan="+"2"+">Total:</td><td>"+total+"</td></tr></table>";
	}
	
	
	
	
	
	
}
