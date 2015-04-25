package at.reisisoft.convert.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import at.reisisoft.convert.Document;
import at.reisisoft.convert.dataAbstractor.DataAbstractor;

import com.sun.istack.internal.ByteArrayDataSource;

public class MailHelper {

	public static Message getMail(final MailSettings settings, final String to,
			final String subject, final String body, final Document attachment,
			final DataAbstractor dataAbstractor) throws MessagingException {
		final Properties properties = new Properties();
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", "false");
		properties.put("mail.smtp.host", settings.getSmtp());
		final Session session = Session.getInstance(properties,
				new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(settings.getFrom(),
						settings.getPw());
			}
		});
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(settings.getFrom()));
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);

		final MimeBodyPart bodyPart = new MimeBodyPart();
		final Multipart mainMail = new MimeMultipart();
		bodyPart.setText(body);
		bodyPart.setContent(body, "text/html");
		mainMail.addBodyPart(bodyPart);
		if ((attachment != null) && (dataAbstractor != null)) {
			final BodyPart attachementPart = new MimeBodyPart();
			attachementPart.setFileName(attachment.getFilename());
			final DataSource attachmentSource = new ByteArrayDataSource(
					attachment.getData(),
					dataAbstractor.getMimetype(attachment));
			attachementPart.setDataHandler(new DataHandler(attachmentSource));
			mainMail.addBodyPart(attachementPart);
		}
		message.setContent(mainMail);
		return message;
	}

	public static void send(final Message m) throws MessagingException {
		m.setSentDate(new Date());
		Transport.send(m);
	}
}
