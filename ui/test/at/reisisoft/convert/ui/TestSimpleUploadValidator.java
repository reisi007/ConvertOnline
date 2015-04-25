package at.reisisoft.convert.ui;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.LinkedList;

import at.reisisoft.convert.components.uploadvalidator.SimpleUploadValidator;
import at.reisisoft.convert.components.uploadvalidator.FileTypeValidator;

import org.junit.Test;

public class TestSimpleUploadValidator {

	@Test
	public void test() {
		Collection<String> collection = new LinkedList<>();
		collection.add("doc");
		final FileTypeValidator validator = new SimpleUploadValidator(collection,
				collection);
		assertTrue(validator.isValidExport("test.doc"));
		assertTrue(validator.isValidImport("test.doc"));
	}

}
