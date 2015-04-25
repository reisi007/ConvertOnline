package at.reisisoft.convert.ui;

import java.util.Calendar;

import javax.servlet.annotation.WebServlet;

import at.reisisoft.convert.pages.ScalcPage;
import at.reisisoft.convert.pages.SdrawPage;
import at.reisisoft.convert.pages.SimpressPage;
import at.reisisoft.convert.pages.SmathPage;
import at.reisisoft.convert.pages.StartPage;
import at.reisisoft.convert.pages.SwriterPage;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("converterTheme")
@Title("Loading...")
public class ConverterVaadinMain extends UI {
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = true, ui = ConverterVaadinMain.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(final VaadinRequest request) {
		Panel container = new Panel();
		container.setSizeUndefined();
		final Navigator navigator = new Navigator(this, container);

		navigator.addProvider(new Navigator.ClassBasedViewProvider("",
				StartPage.class));

		navigator.addProvider(new Navigator.ClassBasedViewProvider(
				SwriterPage.URL, SwriterPage.class));
		navigator.addProvider(new Navigator.ClassBasedViewProvider(
				ScalcPage.URL, ScalcPage.class));
		navigator.addProvider(new Navigator.ClassBasedViewProvider(
				SimpressPage.URL, SimpressPage.class));
		navigator.addProvider(new Navigator.ClassBasedViewProvider(
				SmathPage.URL, SmathPage.class));
		navigator.addProvider(new Navigator.ClassBasedViewProvider(
				SdrawPage.URL, SdrawPage.class));

		navigator.setErrorView(StartPage.class);

		VerticalLayout layout = new VerticalLayout();
		setContent(layout);
		layout.setMargin(true);
		Component header = getHeader();
		Component footer = getFooter();
		layout.addComponent(header);
		layout.addComponent(container);
		layout.addComponent(footer);

		layout.setComponentAlignment(header, Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(container, Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(footer, Alignment.MIDDLE_CENTER);
	}

	private Component getHeader() {
		Label l = new Label(
				String.format(
						"<a href='#' class='invisibleLink' ><h1> <span style='font-variant:small-caps;'>%s</span> <span style='font-size: 80%%;'>%s</span></h1></a>",
						StartUp.ProductData.COMP, StartUp.ProductData.PROD),
						ContentMode.HTML);
		l.setWidth(null);
		return l;
	}

	private Component getFooter() {
		StringBuilder sb = new StringBuilder("<p>© Reisisoft 2015");
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (year > 2015) {
			sb.append(" - ").append(year);
		}
		Label l = new Label(sb.toString(), ContentMode.HTML);
		l.setWidth(null);
		return l;
	}

}