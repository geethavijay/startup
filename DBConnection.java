import java.io.IOException;
import java.sql.*;

public class DBConnection {
	public static void main(String args[]) throws IOException {
		Connection conn = getDBConnection("SQLServer", "WebSiteDB",
				"sa", "manager", "10.xx.xxx.xxx", "1433");
		String selectQuery = "select id,name,age from Test";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectQuery);
			while (rs.next()) {
				System.out.println(rs.getInt(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static Connection getDBConnection(String database, String databaseName,
			String dbUser, String dbPassword, String dbHost, String dbPort)
			throws IOException {
		Connection con = null;
		String DB_URL = null;
		String dbDetails = dbHost + ":" + dbPort;

		try {
			if (database.equalsIgnoreCase("SQLServer")) {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				DB_URL = "jdbc:sqlserver://" + dbDetails + ";databaseName="
						+ databaseName;
			} else if (database.equalsIgnoreCase("Oracle")) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				DB_URL = "jdbc:oracle:thin:@" + dbDetails + ":" + databaseName;
			} else if (database.equalsIgnoreCase("DB2")) {
				Class.forName("com.ibm.db2.jcc.DB2Driver");
				DB_URL = "jdbc:db2://" + dbDetails + "/" + databaseName;
			} else if (database.equalsIgnoreCase("Sybase")) {
				Class.forName("com.sybase.jdbc3.jdbc.SybDriver");
				DB_URL = "jdbc:sybase:Tds:" + dbDetails + "/" + databaseName;
			} else if (database.equalsIgnoreCase("Postgres")) {
				Class.forName("org.postgresql.Driver");
				DB_URL = "jdbc:postgresql://" + dbDetails + "/" + databaseName;
			} else {
				return null;
			}
			System.out.println("\nDatabase URL:" + DB_URL);
			con = DriverManager.getConnection(DB_URL, dbUser, dbPassword);
			System.out.println("\nSuccessfully connected to database....");
			return con;
		} catch (SQLException e) {
			System.out.println("\nERROR..." + e.getMessage());
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("\nERROR..." + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
