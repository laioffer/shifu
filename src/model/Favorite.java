package model;

public class Favorite {
	
	private String userId;
	private String jobId;

	public Favorite(String userId, String jobId) {
		super();
		this.userId = userId;
		this.jobId = jobId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
}
