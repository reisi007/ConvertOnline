package at.reisisoft.convert.components.uploadvalidator;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SimpleUploadValidator implements FileTypeValidator {

	private final SortedSet<String> exportExtensions = new TreeSet<>(),
			importExtensions = new TreeSet<>();
	boolean wasFile = false;

	public SimpleUploadValidator(final Collection<String> e,
			final Collection<String> i) {
		exportExtensions.addAll(e);
		importExtensions.addAll(i);
	}

	@Override
	public boolean isValidExport(final String ext) {
		wasFile = ext.length() > 0;
		return exportExtensions.contains(ext.toLowerCase());
	}

	@Override
	public Set<String> getListOfExportExtensions() {
		return Collections.unmodifiableSet(exportExtensions);
	}

	@Override
	public boolean isValidImport(final String ext) {
		wasFile = ext.length() > 0;
		return importExtensions.contains(ext.toLowerCase());
	}

	@Override
	public Set<String> getListOfImportExtensions() {
		return Collections.unmodifiableSet(importExtensions);
	}

	@Override
	public boolean wasFile() {
		return wasFile;
	}
}
