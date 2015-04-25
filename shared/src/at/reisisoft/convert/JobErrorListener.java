package at.reisisoft.convert;

import java.io.Serializable;

public interface JobErrorListener extends Serializable {

	public void onException(String error);

	public void onException(Throwable cause);
}
