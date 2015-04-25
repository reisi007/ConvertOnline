package at.reisisoft.convert.worker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import at.reisisoft.convert.Document;
import at.reisisoft.convert.Documents;
import at.reisisoft.convert.FileNameUtils;
import at.reisisoft.convert.Job;
import at.reisisoft.convert.worker.threadsafe.LibOConvertProcessBuilder;

import org.apache.commons.io.FileUtils;

public class JobExecutor {

	public static void doJob(final Job job) {
		File tempFolder = null;
		try {
			Document indoc = job.getDocument();
			tempFolder = Files.createTempDirectory("convert").toFile();
			File input = new File(tempFolder, indoc.getFilename());
			File output = FileNameUtils.getConvertedFile(input,
					job.getExportExtension());
			FileUtils.writeByteArrayToFile(input, indoc.getData());
			LibOConvertProcessBuilder processBuilder = new LibOConvertProcessBuilder(
					input, job.getExportExtension());
			processBuilder.start().join();
			job.setFinished(Documents.fromFile(output),
					StartUp.getConverterInformation());
		} catch (Exception e) {
			job.fireJobErrorListener(e);
		} finally {
			if (tempFolder != null) {
				try {
					FileUtils.forceDelete(tempFolder);
				} catch (IOException e) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
					}
					try {
						FileUtils.forceDelete(tempFolder);
					} catch (IOException e1) {

					}
				}
			}
		}
	}
}
