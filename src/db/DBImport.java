package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Create DB tables in MySQL.
 *
 */
public class DBImport {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = null;

			try {
				System.out.println("\nDBImport URL " + DBUtil.URL);
				conn = DriverManager.getConnection(DBUtil.URL);
			} catch (Exception e) {
				System.out.println("SQLException " + e.getMessage());
			}
			if (conn == null) {
				return;
			}
			// Step 1 Drop tables in case they exist.
			Statement stmt = conn.createStatement();
			// Good practice to add shifu_ to avoid possible conflicting table
			// names.
			String sql = "DROP TABLE IF EXISTS shifu_favorite";
			stmt.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS shifu_position";
			stmt.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS shifu_user";
			stmt.executeUpdate(sql);


			// Step 2: create tables
			sql = "CREATE TABLE shifu_position "
					+ "(id VARCHAR(255) NOT NULL, " 
					+ "title VARCHAR(255), "
					+ "location VARCHAR(255), "
					+ "type VARCHAR(255), "
					+ "sanitized_description VARCHAR(1000), "
					+ "apply_link VARCHAR(255), "
					+ "company VARCHAR(255), "
					+ "company_url VARCHAR(255), "
					+ "company_logo VARCHAR(255), " 
					+ "url VARCHAR(255), "
					+ "keywords VARCHAR(255), "
					+ "PRIMARY KEY ( id ))";
			System.out.println("\nDBImport executing query:\n" + sql);
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE shifu_user "
					+ "(user_id VARCHAR(255) NOT NULL, "
					+ "first_name VARCHAR(255), "
					+ "last_name VARCHAR(255), "
					+ "PRIMARY KEY ( user_id ))";
			System.out.println("\nDBImport executing query:\n" + sql);
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE shifu_favorite "
					+ "(favorite_id bigint(20) unsigned NOT NULL AUTO_INCREMENT, "
					+ " user_id VARCHAR(255) NOT NULL , "
					+ " position_id VARCHAR(255) NOT NULL, "
					+ " last_visited_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, "
					+ " PRIMARY KEY (favorite_id),"
					+ "FOREIGN KEY (position_id) REFERENCES shifu_position(id), "
					+ "FOREIGN KEY (user_id) REFERENCES shifu_user(user_id))";
			System.out.println("\nDBImport executing query:\n" + sql);
			stmt.executeUpdate(sql);

			// Step 3: insert data
			// Create a fake user
			sql = "INSERT INTO shifu_user "
					+ "VALUES (\"1111\", \"John\", \"Smith\")";
			System.out.println("\nDBImport executing query:\n" + sql);
			stmt.executeUpdate(sql);

			System.out.println("DBImport: import is done successfully.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
