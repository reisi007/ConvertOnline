package at.reisisoft.convert.worker;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import at.reisisoft.convert.ConverterInformation;

import org.apache.commons.io.IOUtils;

public class OfficeVersionHelper {

	public static String getProduct() {
		return "LibreOffice";
	}

	public static String getUrl() {
		return "http://libreoffice.org/download";
	}

	public static ConverterInformation getConverterInformation()
			throws IOException {
		File soffice = new File(StartUp.SETTINGS.getPathToLibO());
		if (!soffice.exists()) {
			throw new FileNotFoundException("soffice path incorrect! IS: "
					+ soffice);
		}
		File mainXsd = new File(new File(new File(soffice.getParentFile()
				.getParentFile(), "share"), "registry"), "main.xcd");
		String file = IOUtils.toString(new FileInputStream(mainXsd), "utf-8");
		ConverterInformation converterInformation = new ConverterInformation();
		converterInformation.setVersion(getValue(file,
				"ooSetupVersionAboutBox\"><value>", "</value>"));
		converterInformation.setUrl(getValue(file,
				"InfoURL\" oor:type=\"xs:string\"><value>", "</value>"));
		return converterInformation;
	}

	private static String getValue(final String from, final String before,
			final String after) throws EOFException {
		int startIndex = -1, endIndex = -1;
		startIndex = from.indexOf(before);
		if (startIndex < 0) {
			throw new EOFException("main.xcd is not valid, no version found");
		}
		endIndex = from.indexOf(after, startIndex);
		if (endIndex < 0) {
			throw new EOFException("main.xcd is not valid, no version found");
		}
		String version = from.substring(startIndex + before.length(), endIndex);
		return version;
	}
}
