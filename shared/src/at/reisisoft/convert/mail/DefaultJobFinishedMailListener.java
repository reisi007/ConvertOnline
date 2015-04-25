package at.reisisoft.convert.mail;

import javax.mail.Message;
import javax.mail.MessagingException;

import at.reisisoft.convert.ConverterInformation;
import at.reisisoft.convert.Job;
import at.reisisoft.convert.JobFinishedListener;
import at.reisisoft.convert.dataAbstractor.DataAbstractor;

public class DefaultJobFinishedMailListener extends MailBase implements
		JobFinishedListener {

	public DefaultJobFinishedMailListener() {
		super();
	}

	public DefaultJobFinishedMailListener(final DataAbstractor dataAbstractor) {
		super(dataAbstractor);
	}

	public DefaultJobFinishedMailListener(final String to,
			final MailSettings settings, final DataAbstractor dataAbstractor) {
		super(to, settings, dataAbstractor);
	}

	private final String mailSuccessfulBody = "Hi <a href='mailto:%s'>%s</a>,<br>\nThanks for using our conversion service!<br>\nYou may find the converted document attached!<br>\nYour document was converted with: %s";

	@Override
	public void jobFinished(final Job result,
			final ConverterInformation converterInformation) {
		try {
			Message message = MailHelper
					.getMail(getSettings(), getTo(), String.format(
							"Converted %s to %s", result.getDocument()
									.getFilename(), result.getFinished()
									.getFilename()), String.format(
							mailSuccessfulBody, getTo(), getTo(),
							converterInformation.toHtml()), result
							.getFinished(), getDataAbstractor());
			MailHelper.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
