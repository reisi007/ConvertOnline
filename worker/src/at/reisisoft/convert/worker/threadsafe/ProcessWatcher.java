package at.reisisoft.convert.worker.threadsafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProcessWatcher extends Thread {

	public ProcessWatcher(Process watch, long timeout) {
		super();
		this.watch = watch;
		this.timeout = timeout;
	}

	private final List<ProcessFinishedListener> processFinishedListeners = Collections
			.synchronizedList(new ArrayList<>());
	private final Process watch;
	private final long timeout;
	private int returnValue;

	@Override
	public void run() {
		try {
			watch.waitFor(timeout, TimeUnit.MILLISECONDS);
		} catch (final InterruptedException e) {
		}
		if (watch.isAlive()) {
			watch.destroyForcibly();
			returnValue = -1;
			fireProcessFinishedListener();
			return;
		}
		try {
			returnValue = watch.exitValue();
		} catch (final IllegalThreadStateException e) {
			returnValue = -1;
		}
		fireProcessFinishedListener();
	}

	public int getReturnValue() {
		return returnValue;
	}

	public void addProcessFinishedListener(ProcessFinishedListener listener) {
		processFinishedListeners.add(listener);
	}

	public void removeProcessFinishedListener(ProcessFinishedListener listener) {
		processFinishedListeners.remove(listener);
	}

	protected void fireProcessFinishedListener() {
		for (final ProcessFinishedListener l : processFinishedListeners) {
			l.processFinished(this);
		}
	}
}