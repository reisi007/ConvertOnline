package at.reisisoft.convert.components;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import at.reisisoft.convert.Document;
import at.reisisoft.convert.Job;
import at.reisisoft.convert.JobErrorListener;
import at.reisisoft.convert.JobFinishedListener;
import at.reisisoft.convert.mail.DefaultDevJobErrorListener;
import at.reisisoft.convert.mail.DefaultEndUserJobErrorListener;
import at.reisisoft.convert.mail.DefaultJobFinishedMailListener;
import at.reisisoft.convert.rmi.JobQueue;
import at.reisisoft.convert.rmi.RmiHelper;
import at.reisisoft.convert.ui.StartUp;
import at.reisisoft.easyrmi.Client;

import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class UploadReceiver implements Receiver, SucceededListener,
		ProgressListener {

	/**
	 *
	 */
	private static final long serialVersionUID = -1414733628739983508L;
	private ByteArrayOutputStream outputStream;
	private String fileName = null;
	private Upload upload;
	private long maxSizeBytes = -1;
	private String email;
	private String[] expoertExtensions;

	public double getMaxSizeMb() {
		return getMaxSizeKb() / 1024;
	}

	public double getMaxSizeKb() {
		return getMaxSizeByte() / 1024d;
	}

	public long getMaxSizeByte() {
		return maxSizeBytes;
	}

	public void setMaxSizeMb(final int mb) {
		setMaxSizeKb(mb * 1024);
	}

	public void setMaxSizeKb(final int kb) {
		setMaxSizeByte(1024 * kb);
	}

	public void setMaxSizeByte(final int b) {
		if (b > 0) {
			maxSizeBytes = b;
		} else {
			maxSizeBytes = -1;
		}
	}

	@Override
	public void uploadSucceeded(final SucceededEvent event) {
		if (event.getLength() == 0) {
			return;
		}
		JobFinishedListener finishedListener = new DefaultJobFinishedMailListener(
				email, StartUp.getMailSettings(),
				StartUp.getMySqlDataAbstractor());
		JobErrorListener devError = new DefaultDevJobErrorListener(
				StartUp.getDevMail(), StartUp.getMailSettings(),
				StartUp.getMySqlDataAbstractor());
		JobErrorListener userError = new DefaultEndUserJobErrorListener(email,
				StartUp.getMailSettings(), StartUp.getMySqlDataAbstractor());
		Job[] jobs = new Job[expoertExtensions.length];
		for (int i = 0; i < jobs.length; i++) {
			jobs[i] = new Job(
					new Document(outputStream.toByteArray(), fileName),
					expoertExtensions[i]);
			jobs[i].addJobFinishedListener(finishedListener);
			jobs[i].addJobJobErrorListener(devError);
			jobs[i].addJobJobErrorListener(userError);
		}

		JobQueue queue;
		try {
			queue = Client.getClient(RmiHelper.QUEUE, StartUp.getRmiServer(),
					JobQueue.class);
		} catch (RemoteException | NotBoundException e) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
			}
			try {
				queue = Client.getClient(RmiHelper.QUEUE, fileName,
						JobQueue.class);
			} catch (RemoteException | NotBoundException e1) {
				for (Job j : jobs) {
					j.fireJobErrorListener(e1);
				}
				return;
			}
		}
		for (Job j : jobs) {
			try {
				queue.addJob(j);
			} catch (RemoteException e) {
				j.fireJobErrorListener(e);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
				}
			}
		}

	}

	@Override
	public OutputStream receiveUpload(final String filename,
			final String mimeType) {
		fileName = filename;
		outputStream = new ByteArrayOutputStream();
		return outputStream;
	}

	@Override
	public void updateProgress(final long readBytes, final long contentLength) {
		if (maxSizeBytes > 0) {
			if (readBytes > maxSizeBytes) {
				upload.interruptUpload();
				return;
			}

		}

	}

	public Upload getUpload() {
		return upload;
	}

	public void setUpload(final Upload upload) {
		this.upload = upload;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String[] getExpoertExtensions() {
		return expoertExtensions;
	}

	public void setExpoertExtensions(final String[] expoertExtensions) {
		this.expoertExtensions = expoertExtensions;
	}

}
