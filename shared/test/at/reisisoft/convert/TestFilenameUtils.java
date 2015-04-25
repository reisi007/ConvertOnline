package at.reisisoft.convert;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestFilenameUtils {
	File before;

	@Before
	public void initialise() {
		before = File.listRoots()[0];
		before = new File(before, "test");
		before = new File(before, "test.doc");
	}

	@Test
	public void testGetConvertedFile() {
		final String fileEnding = "pdf";
		final String convertedfileName = "test.pdf";
		final File after = FileNameUtils.getConvertedFile(before, fileEnding);
		Assert.assertEquals(convertedfileName, after.getName());
	}

	@Test
	public void testGetExtension() {
		final String fileEnding = "doc";
		Assert.assertEquals(fileEnding,
				FileNameUtils.getExtension(before.getName()));
	}
}
