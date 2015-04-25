package at.reisisoft.convert.worker.multithread;

public interface ExecutionEndedListener<R extends Runnable> {

	public void finished(R r);
}
