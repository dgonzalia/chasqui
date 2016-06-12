package ar.edu.unq.chasqui.services.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Cliente;
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
		
		String password = PasswordGenerator.generateRandomToken(); 
		usuarioService.modificarPasswordUsuario(usuario, Encrypter.encrypt(password));
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
		Cliente c =(Cliente) usuarioService.obtenerUsuarioPorEmail(email);
		this.enviarEmailRecuperoContraseña(email, c.getNickName());
	}
	
	
	
}
