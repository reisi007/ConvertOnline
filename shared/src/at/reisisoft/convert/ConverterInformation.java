package at.reisisoft.convert;

import java.io.Serializable;

public class ConverterInformation implements Serializable {

	public final static String VENDOR = "at.reisisoft.convert.worker.officevendor";

	private String version, productName, url;

	public String getVersion() {
		return version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(final String productName) {
		this.productName = productName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return productName + " " + version + " @ " + url;
	}

	public String toHtml() {
		String convertBase = " <a href='%s' target='_blank'> %s %s </a>";
		return String.format(convertBase, url, productName, version);
	}

}
