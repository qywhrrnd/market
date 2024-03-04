package email;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {
	public void mailSender(EmailDTO dto) throws Exception {
		String host = "smtp.gmail.com";
		String username = "rhwls159";
		String password = "wpuf maza ynqd saib";
		int port = 587;

		String senderMail = "rhwls159@naver.com";
		String senderName ="가지나라";
		String recipient = dto.getEmail();
		String subject = "TEST 가지나라 회원가입 감사 메일";
		String body ="TEST 가지나라에 회원가입 해주셔서 감사합니다";
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");

		Session session = Session.getDefaultInstance(props, new Authenticator() {
			String un = username;
			String pw = password;

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(un, pw);
			}
		});
		session.setDebug(true);
		Message mimeMessage = new MimeMessage(session);
		mimeMessage.addFrom(new InternetAddress[] { new InternetAddress(senderMail, senderName) });//
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));//수신자 주소
		mimeMessage.setSubject(subject);
		mimeMessage.setText(body);
		Transport.send(mimeMessage);

	}
}