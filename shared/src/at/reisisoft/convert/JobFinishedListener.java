package at.reisisoft.convert;

import java.io.Serializable;

public interface JobFinishedListener extends Serializable {

	public void jobFinished(Job result, ConverterInformation information);

}
