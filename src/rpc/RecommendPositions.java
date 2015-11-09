package rpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Make this utility class
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			JSONObject input = new JSONObject(jb.toString());
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
			JSONArray array = new JSONArray(positions);
			
			response.setContentType("application/json");
			response.addHeader("Access-Control-Allow-Origin", "*");
			PrintWriter out = response.getWriter();
			out.print(array);
			out.flush();
			out.close();
		} catch (JSONException e) {
			e.printStackTrace();
		}	
	}

}
