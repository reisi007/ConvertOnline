package at.reisisoft.convert.components.uploadvalidator;

import java.util.Set;

public interface FileTypeValidator {

	public boolean isValidExport(String ext);

	public Set<String> getListOfExportExtensions();

	public boolean isValidImport(String ext);

	public Set<String> getListOfImportExtensions();

	public boolean wasFile();
}
