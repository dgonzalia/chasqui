package ar.edu.unq.chasqui.services.impl;

import java.io.File;
import java.io.FileOutputStream;

import ar.edu.unq.chasqui.model.Imagen;

public class FileSaver {

	
	
	public Imagen guardarImagen(String absolutePath,String username,String fileName,byte[] bytes){
		try{
			File dir = new File(absolutePath+"/usuarios/"+username);
			dir.mkdir();
			File f = new File(absolutePath+"/usuarios/"+username+"/"+fileName);
			f.setWritable(true);
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bytes);
			fos.flush();
			fos.close();
			Imagen imagen = new Imagen();
			imagen.setPath("/imagenes/usuarios/"+username+"/"+fileName);
			imagen.setAbsolutePath(absolutePath+"/usuarios/"+username+"/"+fileName);
			imagen.setNombre(fileName);
			return imagen;			
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}
	
}
