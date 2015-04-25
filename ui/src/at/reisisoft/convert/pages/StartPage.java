package at.reisisoft.convert.pages;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class StartPage extends BaseViewLayout {

	/**
	 *
	 */
	private static final long serialVersionUID = 5600810691215183622L;

	public StartPage() {
		addComponent(new Label(
				"<h2>Choose the component you want to use to convert your file!</h2>",
				ContentMode.HTML));
		VerticalLayout right = new VerticalLayout(), left = new VerticalLayout();
		HorizontalLayout subLayout = new HorizontalLayout(left, right);
		subLayout.setSpacing(true);
		right.setSpacing(true);
		right.setMargin(true);
		left.setSpacing(true);
		left.setMargin(true);
		addComponent(subLayout);
		setComponentAlignment(subLayout, Alignment.MIDDLE_CENTER);
		insertContainer(left, Style.Sw, SwriterPage.URL, SwriterPage.TITLE);
		insertContainer(right, Style.Sc, ScalcPage.URL, ScalcPage.TITLE);
		insertContainer(left, Style.Si, SimpressPage.URL, SimpressPage.TITLE);
		insertContainer(right, Style.Sd, SdrawPage.URL, SdrawPage.TITLE);
		insertContainer(left, Style.Sm, SmathPage.URL, SmathPage.TITLE);
	}

	private static final String baseString = "<h3 class='%s'><a href='#!%s' class='invisibleLink'>%s</a></h3>";

	private void insertContainer(final VerticalLayout l, final Style s,
			final String url, final String name) {
		Label label = new Label(String.format(baseString, s.toString(), url,
				name), ContentMode.HTML);
		label.setWidth(null);
		l.addComponent(label);
		l.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
	}

	private static enum Style {
		Sc("r-scf"), Sd("r-sdf"), Si("r-sif"), Sm("r-smf"), Sw("r-swf");
		private String s;

		private Style(final String s) {
			this.s = s;
		}

		@Override
		public String toString() {
			return s;
		}
	}

	@Override
	protected String getName() {
		return "Start Page";
	}

	@Override
	protected at.reisisoft.convert.pages.BaseViewLayout.Style getDesiredStyle() {
		return at.reisisoft.convert.pages.BaseViewLayout.Style.Default;
	}

}
