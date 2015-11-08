package api.github;

import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GitHubConnection {

	private static final String URL = "https://jobs.github.com/positions.json";

	/**
	 * Search for positions with description and location. 
	 */
	public static JSONArray searchPositions(String description, String location) {
		try {
			//Follow the requirement of GitHub job API and convert space to +. 
			description = description.replaceAll(" ", "+");
			location = location.replaceAll(" ", "+");
			
			String url = URL + "?descrption=" + description + "&location="
					+ location;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			System.out.println("\nGitHubConnection is sending 'GET' request to URL :\n" + url);
			System.out.println("\nGitHubConnection Response Code : " + responseCode);
			
			if (responseCode != 200) {
				System.out.println("\nGitHubConnection cannot connect to GitHub.");
				return null;
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			return new JSONArray(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
