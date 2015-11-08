package model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Position {

	private String id;
	private Time createTime;
	private String title;
	private String location;
	private String type;
	private String description;
	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	private String sanitizedDescription;
	private String applyLink;
	private String company;
	private String companyUrl;
	private String companyLogo;
	private String url;
	private List<Keyword> keywords;
	
	/**
	 * Construct a Position object from a JSONObject if possible.
	 */
	public Position(JSONObject jsonObject) {
		try {
			this.id = jsonObject.getString("id");
			this.createTime = Time.valueOf(jsonObject.getString("created_at"));
			this.title = jsonObject.getString("title");
			this.location = jsonObject.getString("location");
			this.type = jsonObject.getString("type");
			this.description = jsonObject.getString("description");
			this.sanitizedDescription = HTMLParser.parse(jsonObject
					.getString("description"));
			this.applyLink = jsonObject.getString("how_to_apply");
			this.company = jsonObject.getString("company");
			this.companyUrl = jsonObject.getString("company_url");
			this.companyLogo = jsonObject.getString("company_logo");
			this.url = jsonObject.getString("url");
			this.keywords = KeywordExtractor.extract(sanitizedDescription);
		} catch (Exception e) {
			System.out
					.println("Exception in converting a JSON object to a Position object");
			e.printStackTrace();
		}
	}
	
	/**
	 * Rebuild a Position object from DB.
	 */
	public Position() {
		setKeywords("something stored in db for keywords");
	}

	/**
	 * Both equals and hashCode are useful when we put Position in a HashSet to
	 * avoid duplicate Positions.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Position) {
			return false;
		}
		Position anotherPosition = (Position) obj;
		return this.id == anotherPosition.id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	/**
	 * Convert a list of Keywords to a formated string. 
	 */
	public String convertKeywordsToString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < keywords.size(); i ++) {
			Keyword keyword = keywords.get(i);
			sb.append(keyword.toString());
			if (i != keywords.size() - 1) {
				// Add a semicolon to seperate keywords.
				sb.append(";");
			}
		}
		return sb.toString();
	}
	
	private void setKeywords(String keywordStr) {
		String[] args = keywordStr.split(";");
		this.keywords = new ArrayList<>();
		for (String arg : args) {
			this.keywords.add(new Keyword(arg));
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Time getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Time createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApplyLink() {
		return applyLink;
	}

	public void setApplyLink(String applyLink) {
		this.applyLink = applyLink;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSanitizedDescription() {
		return sanitizedDescription;
	}

	public void setSanitizedDescription(String sanitizedDescription) {
		this.sanitizedDescription = sanitizedDescription;
	}
}
