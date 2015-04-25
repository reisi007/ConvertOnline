package at.reisisoft.convert.worker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import at.reisisoft.convert.ConverterInformation;
import at.reisisoft.convert.dataAbstractor.JdbcSettings;
import at.reisisoft.convert.mail.MailHelper;
import at.reisisoft.convert.mail.MailSettings;
import at.reisisoft.convert.rmi.RmiHelper;
import at.reisisoft.convert.worker.multithread.ExecutionEndedListener;
import at.reisisoft.convert.worker.multithread.ThreadRunner;
import at.reisisoft.convert.worker.multithread.Worker;

@WebListener
public class StartUp implements ServletContextListener,
		ExecutionEndedListener<Worker> {
	public static final WorkerSettings SETTINGS = new WorkerSettings();
	private static ConverterInformation information;
	private static List<Worker> threads;
	private static boolean shutdown = false;
	public static final String THREAD_GROUP_NAME = "Workers";

	@Override
	public void contextDestroyed(final ServletContextEvent arg0) {
		shutdown = true;
	}

	@Override
	public void contextInitialized(final ServletContextEvent arg0) {
		final ServletContext context = arg0.getServletContext();
		SETTINGS.setPathToLibO(context.getInitParameter(SETTINGS.PATH_TO_LIBO));
		SETTINGS.setRmiHost(context.getInitParameter(RmiHelper.SERVER));
		SETTINGS.getMailSettings().setFrom(
				context.getInitParameter(MailSettings.FROM));
		SETTINGS.getMailSettings().setSmtp(
				context.getInitParameter(MailSettings.SMTP));
		SETTINGS.getMailSettings().setPw(
				context.getInitParameter(MailSettings.PW));
		String useSSL = context.getInitParameter(MailSettings.STARTSSL);
		if (useSSL != null) {
			SETTINGS.getMailSettings().setUseSSL(Boolean.parseBoolean(useSSL));
		}
		SETTINGS.getJdbcSettings().setUrl(
				context.getInitParameter(JdbcSettings.JDBC_URL));
		SETTINGS.getJdbcSettings().setUn(
				context.getInitParameter(JdbcSettings.JDBC_UN));
		SETTINGS.getJdbcSettings().setPw(
				context.getInitParameter(JdbcSettings.JDBC_PW));
		int threadCount = Integer.parseInt(context
				.getInitParameter(SETTINGS.THREAD_COUNT));
		threads = Collections.synchronizedList(new ArrayList<>(threadCount));
		testMail();
		setConverterInformation(context);
		// Wait for the server to finish initialisation
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		for (int i = 0; i < threadCount; i++) {
			finished(new Worker());
		}

	}

	private void testMail() {
		try {
			MailHelper.send(MailHelper.getMail(SETTINGS.getMailSettings(),
					"mail@example.com", "Test", "123-test", null, null));
		} catch (MessagingException e) {
			throw new RuntimeException("Mail is not configured properly!", e);
		}
	}

	private void setConverterInformation(final ServletContext context) {
		try {
			information = OfficeVersionHelper.getConverterInformation();
			information.setProductName(context
					.getInitParameter(ConverterInformation.VENDOR));
		} catch (IOException e) {
			throw new RuntimeException("LibO is not configured properly!", e);
		}
	}

	public static ConverterInformation getConverterInformation() {
		return information;
	}

	public static String getThreadVisualisation() {
		String baseFormat = "Thread #%s -> %s\n";
		StringBuilder sb = new StringBuilder("Threads:\n");
		for (int i = 0; i < threads.size(); i++) {
			Worker cur = threads.get(i);
			sb.append(String.format(baseFormat, i + 1,
					cur.doesWork() ? "WORKING"
							: (cur.getState() == Thread.State.RUNNABLE ? "IDLE"
									: cur.getState())));
		}
		return sb.toString();
	}

	@Override
	public void finished(final Worker old) {
		threads.remove(old);
		if (!shutdown) {
			Worker w = new Worker();
			threads.add(w);
			ThreadRunner<Worker> wRunner = new ThreadRunner<>(w, this);
			wRunner.start();
		}
	}
}
