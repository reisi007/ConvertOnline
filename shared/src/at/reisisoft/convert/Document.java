package at.reisisoft.convert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

public class Document implements Serializable {

	public Document() {
		this(null, null);
	}

	public Document(final byte[] data, final String filename) {
		super();
		this.data = data;
		this.filename = filename;
	}

	private byte[] data;
	private String filename;

	public byte[] getData() {
		return data;
	}

	public void setData(final byte[] data) {
		this.data = data;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(final String filename) {
		this.filename = filename;
	}

	public boolean toFile(final File file) {
		if (!file.exists()) {
			try {
				if (!file.createNewFile()) {
					return false;
				}
			} catch (final IOException e) {
				return false;
			}
		}
		if (file.isDirectory()) {
			return toFile(new File(file, filename));
		}
		boolean prepWorking;
		try {
			prepWorking = file.delete() && file.createNewFile()
					&& file.canWrite();
		} catch (final IOException e) {
			return false;
		}
		if (!prepWorking) {
			return false;
		}
		try (OutputStream oStream = new FileOutputStream(file);) {
			oStream.write(data);
		} catch (final IOException e) {
			return false;
		}
		return true;

	}
}
