package at.reisisoft.convert.worker.multithread;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import at.reisisoft.convert.Job;
import at.reisisoft.convert.rmi.JobQueue;
import at.reisisoft.convert.rmi.RmiHelper;
import at.reisisoft.convert.worker.JobExecutor;
import at.reisisoft.convert.worker.StartUp;
import at.reisisoft.easyrmi.Client;

public class Worker extends Thread {
	private boolean working;

	@Override
	public void run() {
		try {
			working = false;
			JobQueue queue = Client.getClient(RmiHelper.QUEUE,
					StartUp.SETTINGS.getRmiHost(), JobQueue.class);
			Job job = queue.getJob();
			working = true;
			JobExecutor.doJob(job);
		} catch (RemoteException | NotBoundException e) {
			// TODO Better then ignoring it
		} finally {
			working = false;

		}
	}

	public boolean doesWork() {
		return working;
	}
}
