package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Keyword;
import model.Position;

/**
 * Performs all DB queries
 */
public class DBConnection {
	private Connection conn;

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
	
	private void executeUpdateStatement(String query) {
		try {
			if (conn == null) {
				return;
			}
			Statement stmt = conn.createStatement();
			System.out.println("\nDBConnection executing query:\n" + query);
			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Set<String> executeQueryStatement(String key, String inputKey, String outputKey, String tableName) {
		Set<String> set = new HashSet<>();
		try {
			if (conn == null) {
				return null;
			}
			Statement stmt = conn.createStatement();
			String sql = "SELECT " + outputKey + " from " + tableName + " WHERE " + inputKey + " =\""
					+ key + "\"";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String result = rs.getString(outputKey);
				set.add(result);
			}
			return set;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return set;
	}

	public void insertPosition(Position position) {
		String sql = "INSERT IGNORE INTO shifu_position " + "VALUES (\""
				+ position.getId() + "\", \""
				+ position.getTitle() + "\", \""
				+ position.getLocation() + "\", \""
				+ position.getType() + "\", \"" 
				// May not need to insert sanitized_description if your machine is slow.
				+ position.getSanitizedDescription() + "\", \""
				+ position.getApplyLink() + "\", \""
				+ position.getCompany() + "\", \""
				+ position.getCompanyUrl() + "\" ,\""
				+ position.getCompanyLogo() + "\", \"" 
				+ position.getUrl() + "\", \"" 
				+ Keyword.convertKeywordsToString(position.getKeywords()) + "\")";
		executeUpdateStatement(sql);
	}

	public void setFavoritePositions(String userId, List<String> positionIds) {
		String sql = "";
		for (String positionId : positionIds) {
			sql = "INSERT INTO shifu_favorite (`user_id`, `position_id`) VALUES (\""
					+ userId + "\", \"" + positionId + "\")";
			executeUpdateStatement(sql);
		}
	}
	
	private Set<String> getFavoritePositions(String userId) {
		return executeQueryStatement(userId, "user_id", "position_id", "shifu_favorite");
	}
	
	private List<String> getKeywords(String positionId) {
		Set<String> keywordSet = executeQueryStatement(positionId, "id",
				"keywords", "shifu_position");
		for (String keywords : keywordSet) {
			return Keyword.convertStrToKeywords(keywords);
		}
		return null;
	}
	
	public List<Keyword> getFavoriteKeywords(String userId) {
		List<Keyword> keywords = new ArrayList<>();
		try {
			if (conn == null) {
				return keywords;
			}

			Set<String> positionIds = getFavoritePositions(userId);
			Map<String, Integer> frequencyMap = new HashMap<>();
			for (String positionId : positionIds) {
				for (String keyword : getKeywords(positionId)) {
					if (!frequencyMap.containsKey(keyword)) {
						frequencyMap.put(keyword, 0);
					}
					frequencyMap.put(keyword, 1 + frequencyMap.get(keyword));
				}
			}
			for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
				keywords.add(new Keyword(entry.getKey(), entry.getValue()));
			}
			Collections.sort(keywords);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keywords;
	}
}