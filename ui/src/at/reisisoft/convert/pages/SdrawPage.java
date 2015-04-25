package at.reisisoft.convert.pages;

import at.reisisoft.convert.components.uploadvalidator.MySqlUploadValidator;
import at.reisisoft.convert.dataAbstractor.DataAbstractor.Component;

public class SdrawPage extends AbstractComponentPage {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final String URL = "sd";
	public static final String TITLE = "Convert Draw files!";

	public SdrawPage() {
		super(TITLE, new MySqlUploadValidator(Component.Sd));
	}

	@Override
	protected Style getDesiredStyle() {
		return Style.Sd;
	}
}
