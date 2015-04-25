package at.reisisoft.convert.mail;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.mail.Message;
import javax.mail.MessagingException;

import at.reisisoft.convert.JobErrorListener;
import at.reisisoft.convert.dataAbstractor.DataAbstractor;

public abstract class DefaultAbstractJobErrorListener extends MailBase
implements JobErrorListener {
	public static final String DEV_MAIL = "at.reisisoft.converter.mail.devmail";

	public DefaultAbstractJobErrorListener() {
		super();
	}

	public DefaultAbstractJobErrorListener(final DataAbstractor dataAbstractor) {
		super(dataAbstractor);
	}

	public DefaultAbstractJobErrorListener(final String to,
			final MailSettings settings, final DataAbstractor dataAbstractor) {
		super(to, settings, dataAbstractor);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 4349009426232679519L;

	@Override
	public abstract void onException(final String error);

	@Override
	public abstract void onException(final Throwable cause);

	protected String printStackTraceToString(final Throwable t) {
		StringWriter writer = new StringWriter();
		t.printStackTrace(new PrintWriter(writer));
		return writer.toString();

	}

	protected void onException(final String subject, final String body) {
		try {
			Message message = MailHelper.getMail(getSettings(), getTo(),
					subject, body, null, null);
			MailHelper.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
