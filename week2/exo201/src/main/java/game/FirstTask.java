package game;

import java.io.IOException;
import java.sql.SQLException;

public class FirstTask {
	
	public static void main(String[] args) throws IOException, SQLException {
		// First, we have to register our JDBC Driver, we use SQLite this week.
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			throw new Error(e);
		}
		
		// Next, we have to create a Connection, using database file
		// a file "data.db" would be located in the game quest directory
		
		// Then prepare a statement to delete a user with email equals to "gidebessai@mooc.fun"
		
		// execute that query.
		
		// ensure to close connection
	}

}
