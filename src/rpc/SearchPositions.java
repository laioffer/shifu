package rpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Position;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import api.github.GitHubConnection;

/**
 * Servlet implementation class SearchPositions
 */
@WebServlet("/position")
public class SearchPositions extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchPositions() {
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
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			reader.close();
			
			// Parse input parameters from client.
			JSONObject input = new JSONObject(jb.toString());
			ServletContext context = getServletContext();
			
			if (input.has("description") && input.has("location")) {
				String description = input.getString("description");
				String location = input.getString("location");
				System.out
						.println("SearchPositions gets a POST request with description="
								+ description + " and location=" + location);
				// Fetch Positions.
				response.setContentType("application/json");
				response.addHeader("Access-Control-Allow-Origin", "*");
				PrintWriter out = response.getWriter();
				JSONArray jsonArray = GitHubConnection.searchPositions(description, location);
				out.print(jsonArray);
				out.flush();
				out.close();
				
				DBConnection connection = new DBConnection();
				for (int i = 0; i < jsonArray.length(); i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Position position = new Position(jsonObject, context);
					connection.insertPosition(position);
				}
			} else {
				System.err.println("SearchPositions gets an invalid POST request that "
						+ "does not contain description or location. Return null.");
			}
		} catch (JSONException e) {
			System.out.println("JSON format is wrong");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
