package net.jw.mail;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import net.jw.meeting.model.JWMail;


public class JWMailer {
	
	private MailSender mailSender;
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(JWMail mail) {
		try
		{
			SimpleMailMessage message = new SimpleMailMessage();

			message.setFrom(mail.getFrom());
			message.setTo(mail.getTo());
			if(mail.getCc() !=null)
				message.setCc(mail.getCc());
			if(mail.getBcc() !=null)
				message.setBcc(mail.getBcc());
			message.setSubject(mail.getSubject());
			message.setText(mail.getMessage());
			//message.setContent(message, "text/html");
			mailSender.send(message);
			System.out.println("-----------------------mail sent to : "+mail.getTo()+"------------------------------");
		}
		catch(Exception e)
		{
			System.out.println("-----------------------mail sent failed ------------------------------");
			e.printStackTrace();
		}
	}

}
