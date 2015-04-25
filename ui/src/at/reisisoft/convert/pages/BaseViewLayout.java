package at.reisisoft.convert.pages;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public abstract class BaseViewLayout extends VerticalLayout implements View {

	/**
	 *
	 */
	private static final long serialVersionUID = -1568358909887728851L;
	private static Style last = Style.Default;

	protected BaseViewLayout() {
		setMargin(true);
	}

	@Override
	public final void enter(final ViewChangeEvent event) {
		Page.getCurrent().setTitle(getName());
		Component parent = getParent();
		parent.removeStyleName(last.toString());
		last = getDesiredStyle();
		parent.addStyleName(last.toString());
	}

	protected abstract String getName();

	protected abstract Style getDesiredStyle();

	protected static enum Style {
		Sc("r-sc"), Sd("r-sd"), Si("r-si"), Sm("r-sm"), Sw("r-sw"), Default(
				"r-default");
		private String s;

		private Style(final String s) {
			this.s = s;
		}

		@Override
		public String toString() {
			return s;
		}
	}

}
