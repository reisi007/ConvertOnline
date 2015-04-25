package at.reisisoft.convert;

import java.io.File;

public class FileNameUtils {

	public static File getConvertedFile(File base, String ending) {
		final File folder = base.getParentFile();
		String filename = base.getName();
		final int index = filename.lastIndexOf('.');
		if (index >= 0) {
			filename = filename.substring(0, index + 1);
		}

		return new File(folder, filename + ending);
	}

	public static String getExtension(String filename) {
		final int index = filename.lastIndexOf('.');
		if (index == -1) {
			return filename;
		}
		return filename.substring(index + 1, filename.length());
	}
}
