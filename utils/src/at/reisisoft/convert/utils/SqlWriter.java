package at.reisisoft.convert.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import at.reisisoft.convert.dataAbstractor.DataAbstractor;
import at.reisisoft.convert.dataAbstractor.DataAbstractor.Component;

public class SqlWriter {

	private static final String jdbcConnector = "jdbc:mysql://127.0.0.1:3306/";
	private static final String insertMimeType = "INSERT INTO `converter`.`mimetypes`(`fileEnd`,`mimetype`)VALUES(?,?)";
	private static final String insertImportExport = "INSERT INTO `converter`.`possiblefileendings`	(`component`,	`fileEnd`,	`import`,	`export`)	VALUES	(?,?,?,?);";
	private static Map<String, Set<String>> hmImport;
	private static Map<String, Set<String>> hmExport;
	private static Map<String, String> mimeTypes;

	public static void main(final String[] args) {
		File baseDir = new File(new File(ClassLoader.getSystemClassLoader()
				.getResource(".").getFile()).getParentFile(), "input");
		System.out.println("Code location: " + baseDir);
		// Init the 2 Hashmaps
		hmImport = new HashMap<>();
		hmExport = new HashMap<>();
		mimeTypes = new HashMap<>();
		initMap(hmImport);
		initMap(hmExport);
		// Do reading work
		System.out.println("Beginning to read files");
		for (Component component : DataAbstractor.Component.values()) {
			for (boolean b : new boolean[] { true, false }) {
				readFile(component.toString(), b, new File(baseDir, component
						+ "." + (b ? "write" : "read") + ".txt"));
			}
		}
		System.out.println("Finished readling files");
		// Print to console
		System.out.println("Import");
		printImExMap(hmImport);
		System.out.println("Export");
		printImExMap(hmExport);
		printMimeMap(mimeTypes);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try (java.sql.Connection connection = DriverManager.getConnection(
					jdbcConnector, "root", "");) {
				connection.setAutoCommit(false);
				try (Statement t1 = connection.createStatement();
						Statement t2 = connection.createStatement()) {
					t1.executeUpdate("truncate table `converter`.`mimetypes`");
					t2.executeUpdate("truncate table `converter`.`possiblefileendings`");
				}
				try (PreparedStatement mime = connection
						.prepareStatement(insertMimeType);
						PreparedStatement importExport = connection
								.prepareStatement(insertImportExport)) {
					// Add to preparedStatement here
					mimeTypes
							.forEach((final String key, final String value) -> {
								try {
									mime.setString(1, key);
									mime.setString(2, value);
									mime.addBatch();
								} catch (SQLException e) {
									throw new RuntimeException(e);
								}
							});
					System.out.format("Pushed %s Mimetypes to DB!\n", IntStream
							.of(mime.executeBatch()).sum());
					for (Component c : Component.values()) {
						String s = c.toString();
						Set<String> i = hmImport.get(s);
						Set<String> e = hmExport.get(s);
						for (String fileEnding : i) {
							importExport.setString(1, s);
							importExport.setString(2, fileEnding);
							importExport.setInt(3, 1);
							importExport.setInt(4, e.contains(fileEnding) ? 1
									: 0);
							importExport.addBatch();
						}
						for (String fileEnding : e) {
							if (!i.contains(fileEnding)) {
								importExport.setString(1, s);
								importExport.setString(2, fileEnding);
								importExport.setInt(3, 0);
								importExport.setInt(4, 1);
								importExport.addBatch();
							}
						}
					}
					System.out.format(
							"Pushed %s import/export datatypes to DB!\n",
							IntStream.of(importExport.executeBatch()).sum());
				}
				connection.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void initMap(final Map<String, Set<String>> map) {
		for (Component c : Component.values()) {
			Set<String> list = new HashSet<>();
			map.put(c.toString(), list);
		}
	}

	private static void printImExMap(final Map<String, Set<String>> map) {
		for (String key : map.keySet()) {
			System.out.println("== " + key + " ==");
			Set<String> value = map.get(key);
			value.forEach(System.out::println);
			System.out.println();
		}
	}

	private static void printMimeMap(final Map<String, String> map) {
		System.out.println("== Mimetype mapping ==");
		String base = "%s --> %s\n";
		map.forEach((key, value) -> System.out.format(base, key, value));
	}

	private static void readFile(final String component, final boolean export,
			final File file) {
		Map<String, Set<String>> map = export ? hmExport : hmImport;
		Set<String> set = map.get(component);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), Charset.forName("utf-8")))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] split = line.split("\\|", 2);
				String key = split[0].replaceAll("\\s", "");
				String value = split[1].replaceAll("\\s", "");
				if (!set.contains(key)) {
					set.add(key);
				}
				if (!mimeTypes.containsKey(key)) {
					if (value.length() > 0) {
						mimeTypes.put(key, value);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}