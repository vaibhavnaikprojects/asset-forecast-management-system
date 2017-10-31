package com.zensar.afnls.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.zensar.afnls.init.InitiliazeResourceAtServerStartup;

public class CryptograpgyUtility {

	static PrivateKey privateKey;
	static	PublicKey puxblicKey;
	static String password = "zensar";
	static String alias = "afms";
	static{
		try{
			File file = new File(InitiliazeResourceAtServerStartup.keypath+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"com"+File.separator+"zensar"+File.separator+"afnls"+File.separator+"properties"+File.separator+"afms.jks");
			FileInputStream is = new FileInputStream(file);
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			/* getting the key*/
			keystore.load(is, password.toCharArray());
			privateKey = (PrivateKey)keystore.getKey(alias, password.toCharArray());
			java.security.cert.Certificate cert = keystore.getCertificate(alias); 
			/* Here it prints the public key*/
			
			puxblicKey = cert.getPublicKey();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * @param args the command line arguments
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	public static String encrypt(String value) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException{
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE,puxblicKey);
		byte[] a =  cipher.doFinal(value.getBytes());
		BASE64Encoder ba = new BASE64Encoder();
		String bString = ba.encode(a);
		
		return bString;
	}

	public static String decrypt(String value) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException{
		BASE64Decoder dec = new BASE64Decoder();
		byte[] debyt= dec.decodeBuffer(value);
		Cipher cipher1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher1.init(Cipher.DECRYPT_MODE,privateKey);
		byte[] ab =  cipher1.doFinal(debyt);
		String s = new String(ab);
		
		return s;
	}

	public static void main(String[] args) {/*

		try {
			String a ="Q9b2t/PzXBHU48UieuzfzZqALgdFlSBwmB5t+3+bhgmzHkrqrNgBURMtFkA2E44gHUPIfpod1TjG+UuUdorB2PhjRpkDuR1ZIcyCQcSd2EfTJxMxSt61BHGDwUWRjkMN/RRKSnah6sLZE4/QgylhVYNXbLA0nPcvcku2xhnqNj1OWsC3d0hba3Jf6lwVCDjxOd04DMgbt3atxvTdEEAaODKhxi+t0MkobsICu9ZlyTMDpPZjelGHDxQvzMlEsMHGC9p9PnJGSUuP0AIuS1JV8XrGKDfx0cMdQk92FLjALk6mHsrcISSXkTvcPWFG1Qj0l2Qt1OHsF8fkH7xYfSmPzA==";
			short s= 0;
			String g = "1test";     
			try {
				s = Short.parseShort(g);
				//s = (short)Integer.parseInt(g.trim(), 16);
				System.out.println(s);
			} catch (NumberFormatException e){}     




			String b = Base64.encodeBase64URLSafeString((a.getBytes()));
        	System.out.println(b);
        	//String aa =  Base64.encodeBase64URLSafeString((b.getBytes()));
        	String s =  new String(Base64.decodeBase64(b));
        	System.out.println(s);
        	System.out.println(new String(Base64.decodeBase64(b)));


			// "Happy & Sad" in HTML form.


			 getting data for keystore 


			//PrivateKey key = cert1.getPrivateKey();
			//PublicKey key1= (PrivateKey)key;

			 Get certificate of public key 


			 Here it prints the private key
			System.out.println("\nPrivate Key:");

            String name="22";


            BASE64Decoder dec = new BASE64Decoder();
            byte[] debyt= dec.decodeBuffer(bString);

            Cipher cipher1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher1.init(Cipher.DECRYPT_MODE,privateKey);
            byte[] ab =  cipher1.doFinal(debyt);
            String s = new String(ab);
            System.out.println(s);
			 



			
            String cleartextFile = "C:\\email.txt";
            String ciphertextFile = "D:\\ciphertextRSA.png";

            FileInputStream fis = new FileInputStream(cleartextFile);
            FileOutputStream fos = new FileOutputStream(ciphertextFile);
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);

            byte[] block = new byte[32];
            int i;
            while ((i = fis.read(block)) != -1) {
                cos.write(block, 0, i);
            }
            cos.close();


             computing the signature
            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
            dsa.initSign(key);
            FileInputStream f = new FileInputStream(ciphertextFile);
            BufferedInputStream in = new BufferedInputStream(f);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) >= 0) {
               dsa.update(buffer, 0, len);
           };
           in.close();

            Here it prints the signature
           System.out.println("Digital Signature :");
           System.out.println( dsa.sign());

            Now Exporting Certificate 
           System.out.println("Exporting Certificate. ");
           byte[] buffer_out = cert.getEncoded();
           FileOutputStream os = new FileOutputStream(new File("d:\\signedcetificate.cer"));
           os.write(buffer_out);
           os.close();

            writing signature to output.dat file 
           byte[] buffer_out1 = dsa.sign();
           FileOutputStream os1 = new FileOutputStream(new File("d:\\output.dat"));
           os1.write(buffer_out1);
           os1.close();

			        } catch (Exception e) {
				 System.out.println(e);
				 e.printStackTrace();
			 }

	*/}
}
