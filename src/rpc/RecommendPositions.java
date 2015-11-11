package rpc;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Keyword;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import api.github.GitHubConnection;
import db.DBConnection;

/**
 * Servlet implementation class RecommendPositions
 */
@WebServlet("/recommend")
public class RecommendPositions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int MAX_RECOMMENDED_POSITIONS = 50;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecommendPositions() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject input = RpcParser.parseInput(request);
			DBConnection connection = new DBConnection();
			HashSet<JSONObject> positions = new HashSet<>();//not useful
			if (input.has("user_id") && input.has("location")) {
				String userId = input.getString("user_id");
				String location = input.getString("location");
				List<Keyword> keywords = connection.getFavoriteKeywords(userId);
				for (Keyword keyword : keywords) {
					if (positions.size() > MAX_RECOMMENDED_POSITIONS) {
						break;
					}
					JSONArray jsonArray = GitHubConnection.searchPositions(
							keyword.getKey(), location);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						positions.add(jsonObject);
					}
				}
			}
			RpcParser.parseOutput(response, new JSONArray(positions));
		} catch (JSONException e) {
			e.printStackTrace();
		}	
	}

}
