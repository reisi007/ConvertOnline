package at.reisisoft.convert.pages;

import at.reisisoft.convert.components.UploadComponent;
import at.reisisoft.convert.components.extensionsource.ExtensionListSelector;
import at.reisisoft.convert.components.uploadvalidator.FileTypeValidator;
import at.reisisoft.convert.ui.StartUp;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public abstract class AbstractComponentPage extends BaseViewLayout {

	/**
	 *
	 */
	private static final long serialVersionUID = -5753399085526786871L;
	private final String title;

	public AbstractComponentPage(final String title,
			final FileTypeValidator validator) {
		this.title = title;

		ExtensionListSelector selector = new ExtensionListSelector();
		UploadComponent upploadComponent = new UploadComponent(validator,
				selector, StartUp.getMaxSize(), StartUp.getUnit());
		addComponent(selector);
		selector.addItems(validator.getListOfExportExtensions().toArray());
		Label l = new Label("Available formats for export:");
		l.addStyleName("v-captiontext");
		HorizontalLayout selectorLayout = new HorizontalLayout(l, selector);
		selectorLayout.setSpacing(true);
		selectorLayout.setComponentAlignment(l, Alignment.MIDDLE_CENTER);
		HorizontalLayout layout = new HorizontalLayout(upploadComponent,
				selectorLayout);
		layout.setSpacing(true);
		addComponent(layout);
	}

	@Override
	protected String getName() {
		return title;
	}

}
