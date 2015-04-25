package at.reisisoft.convert.components;

import java.util.Collection;
import java.util.Iterator;

import at.reisisoft.convert.FileNameUtils;
import at.reisisoft.convert.components.extensionsource.ExtensionSource;
import at.reisisoft.convert.components.uploadvalidator.FileTypeValidator;
import at.reisisoft.convert.pages.ThankYouPage;

import com.vaadin.navigator.Navigator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ChangeEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;

public class UploadComponent extends FormLayout implements
		Upload.ChangeListener, Upload.SucceededListener {

	/**
	 *
	 */
	private static final long serialVersionUID = -2024532689567632464L;
	private Upload upload;
	private final Label messageLabel, importFormats;
	private final VerticalLayout uploadLayout;
	private final EmailTextfield email;
	private ExtensionSource exportExtensionSource;
	private final FileTypeValidator fileTypeValidator;
	private final UploadReceiver receiver = new UploadReceiver();

	public static enum Unit {
		KB, MB, B
	}

	public UploadComponent(final FileTypeValidator fileTypeValidator,
			final ExtensionSource exportExtensionSource, final int maxSize,
			final Unit unit) {
		this.fileTypeValidator = fileTypeValidator;
		this.exportExtensionSource = exportExtensionSource;
		email = new EmailTextfield();
		upload = getNewUpload();
		messageLabel = new Label();
		messageLabel.setContentMode(ContentMode.HTML);
		messageLabel.setStyleName("small");
		importFormats = new Label(String.format(
				"Maximum file size: %s %s Supported filetypes: %s", maxSize,
				unit == Unit.B ? "Bytes" : unit.toString(),
						toString(fileTypeValidator.getListOfImportExtensions())),
				ContentMode.HTML);
		importFormats.setStyleName("tiny");

		addComponent(new Label("Convert your documents here for free!"));
		addComponent(email);
		uploadLayout = new VerticalLayout();
		uploadLayout.addComponent(upload);
		uploadLayout.addComponent(importFormats);
		uploadLayout.addComponent(messageLabel);
		uploadLayout.setSpacing(true);
		addComponent(uploadLayout);
		Button submit = new Button("Upload", e -> startUpload());
		submit.setStyleName("small");
		addComponent(submit);
		setSpacing(true);
		switch (unit) {
		case MB:
			receiver.setMaxSizeMb(maxSize);
			break;
		case KB:
			receiver.setMaxSizeKb(maxSize);
			break;
		default:
			receiver.setMaxSizeByte(maxSize);
			break;
		}
	}

	private void resetUpload() {
		final Upload methodUpload = getNewUpload();
		uploadLayout.replaceComponent(upload, methodUpload);
		upload = methodUpload;
	}

	private Upload getNewUpload() {
		final Upload upload = new Upload();
		upload.setButtonCaption(null);
		upload.addChangeListener(this);
		return upload;
	}

	private final String msg = "<strong style='color:%s'> %s </strong><br> %s";
	private final String error = "red";
	private final String success = "forestgreen";

	@Override
	public void filenameChanged(final ChangeEvent event) {

		if (!fileTypeValidator.isValidImport(FileNameUtils.getExtension(event
				.getFilename()))) {
			resetUpload();
			setMessage("Unsupported format",
					"'" + FileNameUtils.getExtension(event.getFilename())
							+ "' is not supported!", true);
		} else {
			setMessage("Valid file for upload!", "", false);
		}

	}

	private void setMessage(final String coloredText,
			final String additionalText, final boolean error) {
		messageLabel.setValue(String.format(msg, error ? this.error : success,
				coloredText, additionalText));
	}

	private void startUpload() {
		String sEmail = email.getValue();
		if (!(email.isValid() && (sEmail.length() > 0))) {
			setMessage("Email not valid!", "Please check your email address!",
					true);
			return;
		}
		Collection<String> exportTo = exportExtensionSource.getSelectedItems();
		if (exportTo.size() <= 0) {

			setMessage("No export filetypes choosen",
					"You must choose at least one filetype to export to!", true);
			return;
		}
		if (fileTypeValidator.wasFile()) {
			setMessage("Uploading....!",
					"Please be patient. This can take some time", false);
		} else {
			setMessage("You must choose a file to upload",
					"Please select a file!", true);
		}

		receiver.setEmail(sEmail);
		receiver.setExpoertExtensions(exportTo.toArray(new String[exportTo
				.size()]));
		receiver.setUpload(upload);
		upload.setReceiver(receiver);
		upload.addSucceededListener(receiver);
		upload.addSucceededListener(this);
		upload.addProgressListener(receiver);
		upload.submitUpload();
	}

	@Override
	public void uploadSucceeded(final SucceededEvent event) {
		Navigator navigator = getUI().getNavigator();
		navigator.addProvider(new Navigator.ClassBasedViewProvider(
				ThankYouPage.URL, ThankYouPage.class));
		navigator.navigateTo(ThankYouPage.URL);

	}

	private String toString(final Iterable<?> iterable) {
		if (iterable == null) {
			return "" + null;
		}
		Iterator<?> iterator = iterable.iterator();
		if (!iterator.hasNext()) {
			return "";
		}
		int i = 1;
		StringBuilder builder = new StringBuilder("<br>");
		builder.append(iterator.next().toString());
		while (iterator.hasNext()) {
			builder.append(", ");
			if (i == 0) {
				builder.append("<br>\n");
			}
			builder.append(iterator.next());
			i = (++i) % 10;
		}

		return builder.toString();
	}
}
