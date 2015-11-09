package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Position;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Performs all DB queries
 */
public class DBConnection {
	private Connection conn;
	private static final int MAX_RECOMMENDED_POSITIONS = 50;
	private static final int MIN_RECOMMENDED_POSITIONS = 3;

	public DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DBUtil.URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void insertPosition(Position position) {
		try {
			if (conn == null) {
				return;
			}
			Statement stmt = conn.createStatement();
			String sql = "INSERT IGNORE INTO shifu_position " + "VALUES (\""
					+ position.getId() + "\", \""
					+ position.getTitle() + "\", \""
					+ position.getLocation() + "\", \""
					+ position.getType() + "\", \"" 
					+ position.getSanitizedDescription() + "\", \""
					+ position.getApplyLink() + "\", \""
					+ position.getCompany() + "\", \""
					+ position.getCompanyUrl() + "\" ,\""
					+ position.getCompanyLogo() + "\", \"" 
					+ position.getUrl() + "\", \"" 
					+ position.convertKeywordsToString() + "\")";
			System.out.println("\nDBConnection executing query:\n" + sql);
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFavoritePositions(String userId, List<String> positionIds) {
		try {
			if (conn == null) {
				return;
			}
			Statement stmt = conn.createStatement();
			String sql = "";
			for (String positionId : positionIds) {
				sql = "INSERT INTO shifu_favorite (`user_id`, `position_id`) VALUES (\""
						+ userId + "\", \"" + positionId + "\")";
				stmt.executeUpdate(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unsetFavoritePositions(String userId, List<String> positionIds) {
		try {
			if (conn == null) {
				return;
			}
			Statement stmt = conn.createStatement();
			String sql = "";
			for (String positionId : positionIds) {
				sql = "DELETE FROM shifu_favorite WHERE `user_id`=\"" + userId
						+ "\" and `position_id` = \"" + positionId + "\"";
				stmt.executeUpdate(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public JSONArray RecommendRestaurants(String userId) {
	// try {
	// if (conn == null) {
	// return null;
	// }
	//
	// Set<String> visitedRestaurants = getVisitedRestaurants(userId);
	// Set<String> allCategories = new HashSet<>();// why hashSet?
	// for (String restaurant : visitedRestaurants) {
	// allCategories.addAll(getCategories(restaurant));
	// }
	// Set<String> allRestaurants = new HashSet<>();
	// for (String category : allCategories) {
	// Set<String> set = getBusinessId(category);
	// allRestaurants.addAll(set);
	// }
	// Set<JSONObject> diff = new HashSet<>();
	// int count = 0;
	// for (String business_id : allRestaurants) {
	// // Perform filtering
	// if (!visitedRestaurants.contains(business_id)) {
	// diff.add(getRestaurantsById(business_id));
	// count++;
	// if (count >= MAX_RECOMMENDED_RESTAURANTS) {
	// break;
	// }
	// }
	// }
	//
	// if (count < MIN_RECOMMENDED_RESTAURANTS) {
	// allCategories.addAll(getMoreCategories(allCategories));
	// for (String category : allCategories) {
	// Set<String> set = getBusinessId(category);
	// allRestaurants.addAll(set);
	// }
	// for (String business_id : allRestaurants) {
	// if (!visitedRestaurants.contains(business_id)) {
	// diff.add(getRestaurantsById(business_id));
	// count++;
	// if (count >= MAX_RECOMMENDED_RESTAURANTS) {
	// break;
	// }
	// }
	// }
	// }
	//
	// return new JSONArray(diff);
	// } catch (Exception e) { /* report an error */
	// System.out.println(e.getMessage());
	// }
	// return null;
	// }

	public static void main(String[] args) {
		// This is for test purpose
		DBConnection conn = new DBConnection();
	}
}