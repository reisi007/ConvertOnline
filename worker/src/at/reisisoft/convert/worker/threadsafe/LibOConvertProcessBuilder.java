package at.reisisoft.convert.worker.threadsafe;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import at.reisisoft.convert.worker.StartUp;

public class LibOConvertProcessBuilder {
	private final ProcessBuilder processBuilder;
	public final static long defaultTimeout = 180000;

	public LibOConvertProcessBuilder(final File toConvert,
			final String convertTo) {
		final List<String> args = new LinkedList<>();
		args.add(StartUp.SETTINGS.getPathToLibO());
		args.add("--headless");
		args.add("--convert-to");
		args.add(convertTo);
		args.add("--outdir");
		args.add(toConvert.getParent());
		args.add(toConvert.toString());

		processBuilder = new ProcessBuilder(args);
	}

	public ProcessWatcher start() throws IOException {
		return start(defaultTimeout);
	}

	public ProcessWatcher start(final ProcessFinishedListener listener)
			throws IOException {
		return start(defaultTimeout, listener);
	}

	public ProcessWatcher start(final long timeout) throws IOException {
		return start(timeout, null);
	}

	public ProcessWatcher start(final long timeout,
			final ProcessFinishedListener listener) throws IOException {
		final Process p = processBuilder.start();
		final ProcessWatcher watcher = new ProcessWatcher(p, timeout);
		if (listener != null) {
			watcher.addProcessFinishedListener(listener);
		}
		watcher.start();
		return watcher;
	}

}
