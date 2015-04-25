package at.reisisoft.convert.pages;

import at.reisisoft.convert.components.uploadvalidator.MySqlUploadValidator;
import at.reisisoft.convert.dataAbstractor.DataAbstractor.Component;

public class SimpressPage extends AbstractComponentPage {
	/**
	 *
	 */
	private static final long serialVersionUID = 10929206326650255L;
	public static final String URL = "si";
	public static final String TITLE = "Convert Impress files!";

	public SimpressPage() {
		super(TITLE, new MySqlUploadValidator(Component.Si));
	}

	@Override
	protected Style getDesiredStyle() {
		return Style.Si;
	}
}
