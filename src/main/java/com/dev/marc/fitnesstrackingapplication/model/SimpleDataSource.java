package com.dev.marc.fitnesstrackingapplication.model;
// This class provides a simple implementation of the DataSource interface.

// Import required classes for DataSource functionality.
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

// SimpleDataSource implements DataSource and delegates connection creation to DatabaseConnection.
public class SimpleDataSource implements DataSource {

	// Returns a Connection object by calling DatabaseConnection.getConnection().
	@Override
	public Connection getConnection() throws SQLException {
		Connection connection = DatabaseConnection.getConnection();  // Get connection from DatabaseConnection.
		if (connection == null) {  // If no connection is returned,
			throw new SQLException("Unable to obtain connection");  // throw an exception.
		}
		return connection;  // Return the connection.
	}

	// Overloaded method to get a connection with username and password.
	// Here, we ignore the parameters because our connection info comes from environment variables.
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getConnection();
	}

	// The following methods are stubs. They throw UnsupportedOperationException since we don't need them for our basic implementation.
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
