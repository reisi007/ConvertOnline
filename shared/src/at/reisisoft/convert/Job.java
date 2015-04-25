package at.reisisoft.convert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Job implements Serializable {
	public Job() {
		finished = null;
	}

	public Job(final Document document, final String exportExtension) {
		super();
		this.document = document;
		finished = null;
		this.exportExtension = exportExtension;
	}

	private Document document;
	private Document finished;
	private final List<JobFinishedListener> jobFinishedListeners = new ArrayList<>();
	private final List<JobErrorListener> jobErrorListeners = new ArrayList<>();
	private boolean fired = false;
	private String exportExtension;

	public void setDocument(final Document document) {
		this.document = document;
	}

	public void setExportExtension(final String exportExtension) {
		this.exportExtension = exportExtension;
	}

	public void addJobFinishedListener(final JobFinishedListener listener) {
		jobFinishedListeners.add(listener);
	}

	public void removeJobFinishedListener(final JobFinishedListener listener) {
		jobFinishedListeners.remove(listener);
	}

	public void addJobJobErrorListener(final JobErrorListener listener) {
		jobErrorListeners.add(listener);
	}

	public void removeJobErrorListener(final JobErrorListener listener) {
		jobErrorListeners.remove(listener);
	}

	protected void fireJobFinishedListeners(
			final ConverterInformation converterInformation) {
		if (finished != null) {
			fired = true;
			for (final JobFinishedListener jobFinishedListener : jobFinishedListeners) {
				jobFinishedListener.jobFinished(this, converterInformation);
			}
		}
	}

	public void fireJobErrorListener(final Throwable t) {
		for (JobErrorListener listener : jobErrorListeners) {
			listener.onException(t);
		}
	}

	public void fireJobErrorListener(final String s) {
		for (JobErrorListener listener : jobErrorListeners) {
			listener.onException(s);
		}
	}

	public Document getFinished() {
		return finished;
	}

	public void setFinished(final Document finished,
			final ConverterInformation converterInformation) {
		if (finished == null) {
			return;
		}
		if (fired) {
			throw new IllegalStateException(
					"Finished document can only be set once!");
		}
		this.finished = finished;
		fireJobFinishedListeners(converterInformation);
	}

	public Document getDocument() {
		return document;
	}

	public String getExportExtension() {
		return exportExtension;
	}

}
