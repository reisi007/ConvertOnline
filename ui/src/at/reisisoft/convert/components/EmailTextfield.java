package at.reisisoft.convert.components;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.TextField;

public class EmailTextfield extends TextField {
	/**
	 *
	 */
	private static final long serialVersionUID = 8358407161237633128L;
	private boolean lastValidationResult = false;

	public EmailTextfield() {
		super("E-Mail");
		addValidator(new EmailValidator("Please enter a valid email address!"));
		setValidationVisible(false);
		addStyleName("small");
	}

}
