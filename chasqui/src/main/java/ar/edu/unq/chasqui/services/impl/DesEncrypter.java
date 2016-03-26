package ar.edu.unq.chasqui.services.impl;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DesEncrypter {

	private Cipher ecipher;
    private Cipher dcipher;

    public DesEncrypter () {}
    
    public DesEncrypter (SecretKey key) throws Exception {
      ecipher = Cipher.getInstance("DES");
      dcipher = Cipher.getInstance("DES");
      ecipher.init(Cipher.ENCRYPT_MODE, key);
      dcipher.init(Cipher.DECRYPT_MODE, key);
    }

    
	@SuppressWarnings("restriction")
	private String encrypt(String str) throws Exception {
      // Encode the string into bytes using utf-8
      byte[] utf8 = str.getBytes("UTF8");

      // Encrypt
      byte[] enc = ecipher.doFinal(utf8);

      // Encode bytes to base64 to get a string
      return new sun.misc.BASE64Encoder().encode(enc);
    }

    private String decrypt(String str) throws Exception {
      // Decode base64 to get bytes
      @SuppressWarnings("restriction")
	byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

      byte[] utf8 = dcipher.doFinal(dec);

      // Decode using utf-8
      return new String(utf8, "UTF8");
    }
    
    public String mkEncrypt (String value) throws Exception {
    	SecretKey key = KeyGenerator.getInstance("DES").generateKey();
    	DesEncrypter encrypter = new DesEncrypter(key);
    	return encrypter.encrypt(value);
    }
    
    public String mkdecrypt (String value) throws Exception{
        SecretKey key = KeyGenerator.getInstance("DES").generateKey();
        DesEncrypter encrypter = new DesEncrypter(key);
        return encrypter.decrypt(value);
    }
}
