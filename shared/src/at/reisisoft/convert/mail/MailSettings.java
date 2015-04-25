package at.reisisoft.convert.mail;

import java.io.Serializable;

public class MailSettings implements Serializable {
	public final static String FROM = "at.reisisoft.convert.email.from";
	public final static String SMTP = "at.reisisoft.convert.email.smtp";
	public final static String PW = "at.reisisoft.convert.email.pw";
	public final static String STARTSSL = "at.reisisoft.convert.email.usessl";

	private String from, smtp, pw;
	private boolean useSSL = false;

	public boolean isUseSSL() {
		return useSSL;
	}

	public void setUseSSL(final boolean useSSL) {
		this.useSSL = useSSL;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(final String from) {
		this.from = from;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(final String smtp) {
		this.smtp = smtp;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(final String pw) {
		this.pw = pw;
	}

	@Override
	public String toString() {
		return "MailSettings [from=" + from + ", smtp=" + smtp + ", pw="
				+ (pw.length() > 0) + ", useSSL=" + useSSL + "]";
	}

}
