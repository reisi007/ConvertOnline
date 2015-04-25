package at.reisisoft.convert.pages;

import at.reisisoft.convert.ui.StartUp;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ThankYouPage extends BaseViewLayout {

	public static final String URL = "uploaded";

	/**
	 *
	 */
	private static final long serialVersionUID = 1517114868068803284L;

	public ThankYouPage() {
		addComponent(new Label(String.format(
				"<h2>Thank you for using %s!</h2>",
				StartUp.ProductData.FULL_NAME), ContentMode.HTML));
		addComponent(new Label("Hit the back button to convert another file!"));
	}

	@Override
	protected String getName() {
		return "Thank You!";
	}

	@Override
	protected Style getDesiredStyle() {
		return Style.Default;
	}

}
