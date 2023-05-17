package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

	Connection c = null;
	
	public DBConnection() {}

	public Connection connDb() {
		try {
			this.c=DriverManager.getConnection("jdbc:mariadb://localhost:3333/hospital?user=root&password=admin");
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
}
