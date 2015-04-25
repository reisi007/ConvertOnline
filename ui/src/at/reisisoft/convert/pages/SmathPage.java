package at.reisisoft.convert.pages;

import at.reisisoft.convert.components.uploadvalidator.MySqlUploadValidator;
import at.reisisoft.convert.dataAbstractor.DataAbstractor.Component;

public class SmathPage extends AbstractComponentPage {
	/**
	 *
	 */
	private static final long serialVersionUID = -2381343399942548933L;
	public static final String URL = "sm";

	public static final String TITLE = "Convert Math files!";

	public SmathPage() {
		super(TITLE, new MySqlUploadValidator(Component.Sm));
	}

	@Override
	protected Style getDesiredStyle() {
		return Style.Sm;
	}
}
