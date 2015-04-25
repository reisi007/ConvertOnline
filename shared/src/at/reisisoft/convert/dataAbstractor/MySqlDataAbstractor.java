package at.reisisoft.convert.dataAbstractor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

public class MySqlDataAbstractor implements DataAbstractor {

	private JdbcSettings settings;

	public MySqlDataAbstractor() {
		this(null);
	}

	public MySqlDataAbstractor(final JdbcSettings settings) {
		this.settings = settings;
	}

	public JdbcSettings getSettings() {
		return settings;
	}

	public void setSettings(final JdbcSettings settings) {
		this.settings = settings;
	}

	private Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new SQLException(e);
		}
		Connection con = DriverManager.getConnection(settings.getUrl(),
				settings.getUn(), settings.getPw());
		con.setAutoCommit(false);
		return con;
	}

	String sqlMimeType = "SELECT mimetype FROM converter.mimetypes where fileEnd = ?";

	@Override
	public String getMimetype(final String extension) {
		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection
					.prepareStatement(sqlMimeType)) {
				preparedStatement.setString(1, extension);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						return resultSet.getString("mimetype");
					}
					return null;
				}
			}
		} catch (Exception e) {
			return null;
		}
	}

	String sqlBaseSupported = "SELECT fileEnd FROM converter.possiblefileendings where component = '%s' AND %s =1";

	@Override
	public Collection<String> getSupportedExtensions(final Component c,
			final boolean export) {
		try (Connection connection = getConnection()) {
			try (Statement statement = connection.createStatement()) {
				try (ResultSet resultSet = statement.executeQuery(String
						.format(sqlBaseSupported, c, export ? "export"
								: "import"))) {
					Collection<String> collection = new LinkedList<>();
					while (resultSet.next()) {
						collection.add(resultSet.getString("fileEnd"));
					}
					return collection;
				}
			}

		} catch (Exception e) {
			return null;
		}
	}

}
