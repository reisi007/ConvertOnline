package at.reisisoft.convert.components.extensionsource;

import java.util.Collection;

import com.vaadin.data.Item;
import com.vaadin.ui.ListSelect;

public class ExtensionListSelector extends ListSelect implements
		ExtensionSource {

	/**
	 *
	 */
	private static final long serialVersionUID = -436710843088598591L;

	public ExtensionListSelector() {
		setMultiSelect(true);
		setStyleName("small");

	}

	@Override
	public Item addItem(final Object itemId)
			throws UnsupportedOperationException {
		if (itemId instanceof String) {
			return super.addItem(itemId);
		}
		throw new UnsupportedOperationException("Cannot add a "
				+ itemId.getClass().getName() + ", only String can be added");
	}

	@Override
	public Collection<String> getSelectedItems() {
		return (Collection<String>) getValue();

	}

}
