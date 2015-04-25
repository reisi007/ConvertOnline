package at.reisisoft.convert.worker;

import java.io.File;

import at.reisisoft.convert.Documents;
import at.reisisoft.convert.Job;
import at.reisisoft.convert.dataAbstractor.MySqlDataAbstractor;
import at.reisisoft.convert.mail.DefaultJobFinishedMailListener;
import at.reisisoft.convert.rmi.JobQueue;
import at.reisisoft.convert.rmi.RmiHelper;
import at.reisisoft.easyrmi.Client;

public class Main {

	public static void main(final String[] args) {
		try {
			System.out.println("Starting");
			final String filename = "D:\\Desktop\\test\\test.doc";
			final File docFile = new File(filename);
			final String liboPath = "L:\\LibreOffice 4.3.5\\program\\soffice.exe";
			final String convertTo = "pdf";
			StartUp.SETTINGS.setPathToLibO(liboPath);
			StartUp.SETTINGS.getMailSettings().setFrom(
					"florian.reisinger@sebtec.net");
			StartUp.SETTINGS.getMailSettings().setSmtp("mail.sebtec.net");
			StartUp.SETTINGS.getMailSettings().setPw("reisisoft-123");
			StartUp.SETTINGS.getJdbcSettings().setUrl(
					"jdbc:mysql://127.0.0.1:3306/");
			StartUp.SETTINGS.getJdbcSettings().setUn("root");
			StartUp.SETTINGS.getJdbcSettings().setPw("");
			// Actual work

			// final LibOProcessBuilder processBuilder = new LibOProcessBuilder(
			// docFile, convertTo);
			// final ProcessWatcher watcher = processBuilder.start(new Main());

			// final Message message = EmailHelper.getMail(
			// StartUp.SETTINGS.getMailSettings(), "reisi007@gmail.com",
			// "Test", "Hallo", Documents.fromFile(docFile));
			// EmailHelper.send(message);
			MySqlDataAbstractor abstractor = StartUp.SETTINGS
					.getMySqlDataAbstractorInstance();
			Job job = new Job(Documents.fromFile(docFile), convertTo);
			job.addJobFinishedListener(new DefaultJobFinishedMailListener(
					"reisi007@gmail.com", StartUp.SETTINGS.getMailSettings(),
					abstractor));
			Client.getClient(RmiHelper.QUEUE, "localhost", JobQueue.class)
			.addJob(job);
			System.out.println("Job added!");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
