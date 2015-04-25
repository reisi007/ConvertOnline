package at.reisisoft.convert.components.uploadvalidator;

import at.reisisoft.convert.dataAbstractor.DataAbstractor;
import at.reisisoft.convert.dataAbstractor.DataAbstractor.Component;
import at.reisisoft.convert.ui.StartUp;

public class MySqlUploadValidator extends SimpleUploadValidator {

	public MySqlUploadValidator(final Component c) {
		this(c, StartUp.getMySqlDataAbstractor());
	}

	public MySqlUploadValidator(final Component c,
			final DataAbstractor dataAbstractor) {
		super(dataAbstractor.getSupportedExtensions(c, true), dataAbstractor
				.getSupportedExtensions(c, false));
	}
}
