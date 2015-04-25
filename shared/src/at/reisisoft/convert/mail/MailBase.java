package at.reisisoft.convert.mail;

import java.io.Serializable;

import at.reisisoft.convert.dataAbstractor.DataAbstractor;

public abstract class MailBase implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 8393978337352371821L;
	private MailSettings settings;
	private String to;
	private DataAbstractor dataAbstractor;

	public MailBase() {
		this(null);
	}

	public MailBase(final DataAbstractor dataAbstractor) {
		this("test@example.com", null, dataAbstractor);
	}

	public MailBase(final String to, final MailSettings settings,
			final DataAbstractor dataAbstractor) {
		this.to = to;
		this.settings = settings;
		this.dataAbstractor = dataAbstractor;
	}

	public DataAbstractor getDataAbstractor() {
		return dataAbstractor;
	}

	public void setDataAbstractor(final DataAbstractor dataAbstractor) {
		this.dataAbstractor = dataAbstractor;
	}

	public MailSettings getSettings() {
		return settings;
	}

	public void setSettings(final MailSettings settings) {
		this.settings = settings;
	}

	public String getTo() {
		return to;
	}

	public void setTo(final String to) {
		this.to = to;
	}
}
