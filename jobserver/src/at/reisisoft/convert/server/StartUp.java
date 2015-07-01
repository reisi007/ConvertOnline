package at.reisisoft.convert.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import at.reisisoft.convert.rmi.JobBlockingQueue;
import at.reisisoft.convert.rmi.JobQueue;
import at.reisisoft.convert.rmi.RmiHelper;
import at.reisisoft.easyrmi.Server;

@WebListener
public class StartUp implements ServletContextListener {
	private static Server<JobQueue> server;
	private static Thread t;
	private static JobQueue jobQueue = null;

	public static int getQueueJobCount() {
		try {
			return jobQueue.count();
		} catch (RemoteException e) {
			return -1;
		}
	}

	@Override
	public void contextDestroyed(final ServletContextEvent arg0) {
		try {
			server.close();
		} catch (IOException e) {
			throw new RuntimeException(
					"Could not shutdown RMI server properly", e);
		} finally {
			saveJobQueue(jobQueue);
			t.interrupt();
		}
	}

	@Override
	public void contextInitialized(final ServletContextEvent arg0) {
		jobQueue = loadJobQueue();
		try {
			server = Server.getServer(new JobBlockingQueue(), RmiHelper.QUEUE);
			server.start();
			t = new Thread() {

				@Override
				public void run() {
					while (!isInterrupted()) {
						jobQueue.hashCode();
						try {
							TimeUnit.DAYS.sleep(1);
						} catch (InterruptedException e1) {
							interrupt();
						}
					}

				};
			};

			t.start();
		} catch (RemoteException e) {
			throw new RuntimeException("Could not start RMI server!", e);
		}

	}

	private JobQueue loadJobQueue() {
		// TODO Load from file if exists
		return new JobBlockingQueue();
	}

	private void saveJobQueue(final JobQueue jobQueue) {
		// TODO Safte to file if exists
	}

	public static Server<JobQueue> getServer() {
		return server;
	}
}
