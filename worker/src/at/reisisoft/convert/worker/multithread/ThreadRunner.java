package at.reisisoft.convert.worker.multithread;

public class ThreadRunner<T extends Thread> extends Thread {
	private T thread;
	private ExecutionEndedListener<T> executionEndedListener;

	public ThreadRunner(final T thread,
			final ExecutionEndedListener<T> executionEndedListener) {
		super();
		this.thread = thread;
		this.executionEndedListener = executionEndedListener;
	}

	@Override
	public void run() {
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {

		} finally {
			if (thread.getState() == Thread.State.TERMINATED) {
				executionEndedListener.finished(thread);
			}
		}

	}

	public T getBaseThread() {
		return thread;
	}

	@Override
	public void setUncaughtExceptionHandler(final UncaughtExceptionHandler arg0) {
		super.setUncaughtExceptionHandler(arg0);
		thread.setUncaughtExceptionHandler(arg0);
	}

}
