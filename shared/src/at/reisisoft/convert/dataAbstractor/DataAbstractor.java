package at.reisisoft.convert.dataAbstractor;

import java.io.Serializable;
import java.util.Collection;

import at.reisisoft.convert.Document;
import at.reisisoft.convert.FileNameUtils;

public interface DataAbstractor extends Serializable {

	public enum Component {
		Sc("sc"), Sd("sd"), Si("si"), Sm("sm"), Sw("sw");
		private String s;

		private Component(final String s) {
			this.s = s;
		}

		@Override
		public String toString() {
			return s;
		}

	};

	public String getMimetype(String extension);

	public default String getMimetype(final Document d) {
		return getMimetype(FileNameUtils.getExtension(d.getFilename()));
	}

	public Collection<String> getSupportedExtensions(Component c, boolean export);
}
