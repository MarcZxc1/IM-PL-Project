package com.dev.marc.fitnesstrackingapplication.model;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class SimpleDataSource implements DataSource {
	@Override
	public Connection getConnection() throws SQLException {
		Connection connection = DatabaseConnection.getConnection();
		if (connection == null) {
			throw new SQLException("Unable to obtain connection");
		}
		return connection;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// If your connection uses environment variables (or is hard-coded), you can ignore these parameters.
		return getConnection();
	}

	// The following methods can be implemented as needed. For now, we'll provide simple stubs.
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Logger getParentLogger() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}
}
