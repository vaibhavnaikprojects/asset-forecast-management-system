package com.zensar.afnls.util;

import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;

import com.zensar.afnls.beans.ITOCCList;
import com.zensar.afnls.beans.RaiseNewHireLaptopRequest;
import com.zensar.afnls.init.InitiliazeResourceAtServerStartup;

public class MailSender {
	static final private String host="javamail.cisco.com";
	static private Properties properties;
	static private Session session;
	@Autowired
	private InitiliazeResourceAtServerStartup starter;
	
	

	static public RaiseNewHireLaptopRequest raiseNewHireLaptopRequest=null;
	static { raiseNewHireLaptopRequest =  MailUitility.readpropfile();
	}
	
	
	static {
		properties=System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		session=Session.getDefaultInstance(properties);
	}
	public boolean sendMailInTableFormat(String subject,String body,String from,String to,String[] cc) {
		try {
			StringTokenizer st = new StringTokenizer(raiseNewHireLaptopRequest.getCcList(),",");
			StringTokenizer sto = new StringTokenizer(raiseNewHireLaptopRequest.getToList(),",");
			
			
			
			MimeMessage message=new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			while(sto.hasMoreElements()){
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(sto.nextToken()));
				}
			if(cc!=null){
			for(String cclist:cc)	{
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(cclist));
			}}
			while(st.hasMoreElements()){
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(st.nextToken()));
			}
			message.setSubject(subject);
			message.setContent(body+"","text/html");
			//message.setText(body);
			Transport.send(message);
			System.out.println("Mail Sent By "+from+" To " +raiseNewHireLaptopRequest.getOdcHead());
		} catch (AddressException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean sendMail(String subject,String body,String from,String to,String cc) {
		try {
			MimeMessage message=new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			if(cc!=null)
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
			message.setSubject(subject +" "+from);
			message.setText(body);
			Transport.send(message);
			System.out.println("Mail Sent By "+from+" To " +to);
		} catch (AddressException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean sendquartermails(String subject,String body,String from,String to,String cc) {
		try {
		
			MimeMessage message=new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			if(cc!=null){
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
				}
			message.setSubject(subject);
			
			BodyPart messageBodyPart = new MimeBodyPart();
			
			messageBodyPart.setContent(body,"text/html");
			
			message.setSubject(subject);
			message.setText(body);
			Multipart multipart=new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			
		} catch (AddressException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean sendAcknowledmentMail(String subject,String body,String from,String to,String cc,String laptopId) {
		try {
		//	from+="@cisco.com";
			from = raiseNewHireLaptopRequest.getAck_mail_id();
		//	to+="@cisco.com";
		//	cc+="@cisco.com";
	//		cc="ajnaik@cisco.com";
			MimeMessage message=new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			if(cc!=null){
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
				}
			message.setSubject(subject);
			
			BodyPart messageBodyPart = new MimeBodyPart();
			
			messageBodyPart.setContent(body,"text/html");
			
			message.setSubject(subject);
			message.setText(body);
			Multipart multipart=new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			
		} catch (AddressException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean sendAcknowledmentMail(String subject,String body,String from,String to,String cc) {
		try {
//			from+="@cisco.com";
					from = raiseNewHireLaptopRequest.getAck_mail_id();
			to+="@cisco.com";
			cc+="@cisco.com";
			MimeMessage message=new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(from));
			message.setSubject(subject);
			message.setText("Report sent to "+to+" with cc "+cc);
			Transport.send(message);
			System.out.println("Mail Acknowledment Sent");
		} catch (AddressException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean remindermail(String subject,String body,String from,String to,String cc) {
		try {
			from+="@cisco.com";
			to+="@cisco.com";
			//cc+="@cisco.com";
			MimeMessage message=new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			
			BodyPart messageBodyPart = new MimeBodyPart();
			
			messageBodyPart.setContent(body,"text/html");
			
			message.setSubject(subject);
			message.setText(body);
			Multipart multipart=new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			
		} catch (AddressException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	
	public boolean sendLaptopClosedMail(String subject,String body,String from,String to) {
		try {
			
			MimeMessage message=new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			Iterator it =  InitiliazeResourceAtServerStartup.itoccList.iterator();
			while(it.hasNext()){
				ITOCCList temp = (ITOCCList) it.next();
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(temp.getCclist()));
				}
BodyPart messageBodyPart = new MimeBodyPart();
			
			messageBodyPart.setContent(body,"text/html");
			
			message.setSubject(subject);
			message.setText(body);
			Multipart multipart=new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			
		} catch (AddressException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean sendMailInTableFormatWithAttachment(String subject,String body,String from,String to,String cc,String filePath,String fileName){
		try {
//			from+="@cisco.com";
//			to+="@cisco.com";
	//		to = raiseNewHireLaptopRequest.getOdcHead();
		//	cc="ajnaik@cisco.com";
			MimeMessage message=new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			if(cc!=null)
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
			message.setSubject(subject);
			
			BodyPart messageBodyPart = new MimeBodyPart();
			
			messageBodyPart.setContent(body,"text/html");
			
			Multipart multipart=new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(filePath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Mail Sent By "+from+" To " +to);
		} catch (AddressException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
