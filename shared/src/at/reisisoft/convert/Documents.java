package at.reisisoft.convert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class Documents {
	public static Document fromFile(File file) throws IOException {
		return new Document(IOUtils.toByteArray(new FileInputStream(file)),
				file.getName());
	}
}
