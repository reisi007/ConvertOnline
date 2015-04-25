package at.reisisoft.convert.rmi;

import java.util.concurrent.LinkedBlockingQueue;

import at.reisisoft.convert.Job;

public class JobBlockingQueue extends LinkedBlockingQueue<Job> implements
JobQueue {
	/**
	 *
	 */
	private static final long serialVersionUID = -7300210637974012505L;

	public JobBlockingQueue() {
		super();
	}

	@Override
	public Job getJob() {
		try {
			return take();
		} catch (final InterruptedException e) {
			return null;
		}
	}

	@Override
	public boolean addJob(Job job) {
		try {
			put(job);
			return true;
		} catch (final InterruptedException e) {
			return false;
		}
	}

	@Override
	public int count() {
		return size();
	}

}
