package at.reisisoft.convert.dataAbstractor;

import java.io.Serializable;

public class JdbcSettings implements Serializable {

	public static final String JDBC_URL = "at.reisisoft.convert.worker.jdbc.url";
	public static final String JDBC_UN = "at.reisisoft.convert.worker.jdbc.un";
	public static final String JDBC_PW = "at.reisisoft.convert.worker.jdbc.pw";
	private String url, un, pw;

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getUn() {
		return un;
	}

	public void setUn(final String un) {
		this.un = un;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(final String pw) {
		this.pw = pw;
	}
}
