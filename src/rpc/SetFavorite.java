package rpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;

/**
 * Servlet implementation class SetFavorite
 */
@WebServlet("/favorite")
public class SetFavorite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetFavorite() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			DBConnection connection = new DBConnection();
			JSONObject input = new JSONObject(jb.toString());
			if (input.has("user_id") && input.has("favorite")) {
				String user_id = input.getString("user_id");
				JSONArray array = input.getJSONArray("favorite");
				List<String> favoriteList = new ArrayList<>(); 
				for (int i = 0; i < array.length(); i ++) {
					String positionId = array.getString(i);
					favoriteList.add(positionId);
				}
				connection.setFavoritePositions(user_id, favoriteList);
			}

			response.setContentType("application/json");
			response.addHeader("Access-Control-Allow-Origin", "*");
			PrintWriter out = response.getWriter();
			JSONObject object = new JSONObject();
			object.put("result", "success");
			out.print(object);
			out.flush();
			out.close();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
