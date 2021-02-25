package game;

import user.UserDao;
import user.UserDaoSqlite;

public class ThirdTask {
	
	public static void main(String[] args) throws Exception {
		// set the userFilePath
		String userFilePath = "set_this_path";
		UserDao dao = new UserDaoSqlite(userFilePath);
		
		// use dao to delete a user
	}

}
