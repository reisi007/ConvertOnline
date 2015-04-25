package at.reisisoft.convert.mail;

import at.reisisoft.convert.dataAbstractor.DataAbstractor;

public class DefaultDevJobErrorListener extends DefaultAbstractJobErrorListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1806045229500966746L;

	public DefaultDevJobErrorListener() {
		super();
	}

	public DefaultDevJobErrorListener(final DataAbstractor dataAbstractor) {
		super(dataAbstractor);
	}

	public DefaultDevJobErrorListener(final String to,
			final MailSettings settings, final DataAbstractor dataAbstractor) {
		super(to, settings, dataAbstractor);
	}

	@Override
	public void onException(final String error) {
		onException("An error occured", error);

	}

	@Override
	public void onException(final Throwable cause) {
		onException(String.format("An %s exception occured", cause.getClass()
				.toString()), printStackTraceToString(cause));

	}

}
