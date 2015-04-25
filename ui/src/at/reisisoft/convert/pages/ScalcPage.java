package at.reisisoft.convert.pages;

import at.reisisoft.convert.components.uploadvalidator.MySqlUploadValidator;
import at.reisisoft.convert.dataAbstractor.DataAbstractor.Component;

public class ScalcPage extends AbstractComponentPage {
	/**
	 *
	 */
	private static final long serialVersionUID = -7145370710562068297L;
	public static final String URL = "sc";
	public static final String TITLE = "Convert Calc files!";

	public ScalcPage() {
		super(TITLE, new MySqlUploadValidator(Component.Sc));
	}

	@Override
	protected Style getDesiredStyle() {
		return Style.Sc;
	}
}
