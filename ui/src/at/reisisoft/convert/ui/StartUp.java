package at.reisisoft.convert.ui;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import at.reisisoft.convert.components.UploadComponent.Unit;
import at.reisisoft.convert.dataAbstractor.JdbcSettings;
import at.reisisoft.convert.dataAbstractor.MySqlDataAbstractor;
import at.reisisoft.convert.mail.DefaultAbstractJobErrorListener;
import at.reisisoft.convert.mail.MailSettings;
import at.reisisoft.convert.rmi.RmiHelper;

@WebListener
public class StartUp implements ServletContextListener {
	public static final String UNIT = "at.reisisoft.worker.upload.unit";
	public static final String MAX_SIZE = "at.reisisoft.worker.upload.maxsize";

	private static MySqlDataAbstractor mysqlDataAbstractor = null;
	private static MailSettings mailSettings = null;
	private static String rmiServer, devMail;
	private static Unit unit;
	private static int maxSize;

	public static Unit getUnit() {
		return unit;
	}

	public static int getMaxSize() {
		return maxSize;
	}

	public static MySqlDataAbstractor getMySqlDataAbstractor() {
		return mysqlDataAbstractor;
	}

	public static MailSettings getMailSettings() {
		return mailSettings;
	}

	@Override
	public void contextDestroyed(final ServletContextEvent arg0) {
		// Nothing to do here

	}

	public static String getRmiServer() {
		return rmiServer;
	}

	public static String getDevMail() {
		return devMail;
	}

	@Override
	public void contextInitialized(final ServletContextEvent arg0) {
		JdbcSettings settings = new JdbcSettings();
		final ServletContext context = arg0.getServletContext();
		settings.setUrl(context.getInitParameter(JdbcSettings.JDBC_URL));
		settings.setUn(context.getInitParameter(JdbcSettings.JDBC_UN));
		settings.setPw(context.getInitParameter(JdbcSettings.JDBC_PW));
		mysqlDataAbstractor = new MySqlDataAbstractor(settings);
		mailSettings = new MailSettings();
		mailSettings.setFrom(context.getInitParameter(MailSettings.FROM));
		mailSettings.setSmtp(context.getInitParameter(MailSettings.SMTP));
		mailSettings.setPw(context.getInitParameter(MailSettings.PW));
		String useSSL = context.getInitParameter(MailSettings.STARTSSL);
		if (useSSL != null) {
			mailSettings.setUseSSL(Boolean.parseBoolean(useSSL));
		}
		rmiServer = context.getInitParameter(RmiHelper.SERVER);
		devMail = context
				.getInitParameter(DefaultAbstractJobErrorListener.DEV_MAIL);
		String unit = context.getInitParameter(StartUp.UNIT);
		maxSize = Integer.parseInt(context.getInitParameter(StartUp.MAX_SIZE));
		if ("MB".equalsIgnoreCase(unit)) {
			StartUp.unit = Unit.MB;
		} else if ("KB".equalsIgnoreCase(unit)) {
			StartUp.unit = Unit.KB;
		} else {
			StartUp.unit = Unit.B;
		}

	}

	public static class ProductData {
		public static final String COMP = "Reisisoft";
		public static final String PROD = "Convert";
		public static final String FULL_NAME = String.format("%s %s", COMP,
				PROD);
	}

}
