package at.reisisoft.convert.worker;

import at.reisisoft.convert.dataAbstractor.JdbcSettings;
import at.reisisoft.convert.dataAbstractor.MySqlDataAbstractor;
import at.reisisoft.convert.mail.MailSettings;

public class WorkerSettings {
	public static final String PATH_TO_LIBO = "at.reisisoft.convert.worker.soffice";
	public static final String THREAD_COUNT = "at.reisisoft.convert.worker.threads";
	private String pathToLibO = null;
	private String rmiHost = null;
	private final MailSettings mail = new MailSettings();
	private final JdbcSettings jdbc = new JdbcSettings();
	private MySqlDataAbstractor mySqlDataAbstractorInstance = null;

	public JdbcSettings getJdbcSettings() {
		return jdbc;
	}

	public MySqlDataAbstractor getMySqlDataAbstractorInstance() {
		if (mySqlDataAbstractorInstance == null) {
			mySqlDataAbstractorInstance = new MySqlDataAbstractor(jdbc);
		}
		return mySqlDataAbstractorInstance;
	}

	public String getPathToLibO() {
		return pathToLibO;
	}

	public void setPathToLibO(final String pathToLibO) {
		this.pathToLibO = pathToLibO;
	}

	public MailSettings getMailSettings() {
		return mail;
	}

	public String getRmiHost() {
		return rmiHost;
	}

	public void setRmiHost(final String rmiHost) {
		this.rmiHost = rmiHost;
	}

}
