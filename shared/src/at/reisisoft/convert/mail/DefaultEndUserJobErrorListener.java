package at.reisisoft.convert.mail;

import at.reisisoft.convert.dataAbstractor.DataAbstractor;

public class DefaultEndUserJobErrorListener extends
		DefaultAbstractJobErrorListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1806045229500966746L;

	public DefaultEndUserJobErrorListener() {
		super();
	}

	public DefaultEndUserJobErrorListener(final DataAbstractor dataAbstractor) {
		super(dataAbstractor);
	}

	public DefaultEndUserJobErrorListener(final String to,
			final MailSettings settings, final DataAbstractor dataAbstractor) {
		super(to, settings, dataAbstractor);
	}

	@Override
	public void onException(final String error) {
		onException(
				"Your document could not be converted!",
				"An email with a detailed error message has been sent to our team!<br>Sorry for the inconvinience!");

	}

	@Override
	public void onException(final Throwable cause) {
		onException(cause.getClass().getName());

	}

}
