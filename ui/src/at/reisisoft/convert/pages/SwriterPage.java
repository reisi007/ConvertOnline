package at.reisisoft.convert.pages;

import at.reisisoft.convert.components.uploadvalidator.MySqlUploadValidator;
import at.reisisoft.convert.dataAbstractor.DataAbstractor.Component;

public class SwriterPage extends AbstractComponentPage {
	public static final String URL = "sw";
	public static final String TITLE = "Convert Writer files!";

	/**
	 *
	 */
	private static final long serialVersionUID = -5565215165378375145L;

	public SwriterPage() {
		super(TITLE, new MySqlUploadValidator(Component.Sw));
	}

	@Override
	protected Style getDesiredStyle() {
		return Style.Sw;
	}
}
