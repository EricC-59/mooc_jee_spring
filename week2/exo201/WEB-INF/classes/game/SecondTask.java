package game;

import java.sql.SQLException;

import user.UserDao;
import user.UserDaoSqlite;

public class SecondTask {
	
	public static void main(String[] args) throws SQLException {
		// set the userFilePath
		String userFilePath = "";
		UserDao dao = new UserDaoSqlite(userFilePath);
		
		// use dao to insert a new user.
		
	}

}
