package model;

public class Keyword implements Comparable<Keyword> {
	private String key;
	private double metric;

	public Keyword(String key, double metric) {
		super();
		this.key = key;
		this.metric = metric;
	}
	
	/**
	 * A constructor that can convert a keyword stored in DB to a Keyword object. 
	 * This keyword is converted from toString().
	 */
	public Keyword(String keyword) {
		String[] args = keyword.split(":");
		this.key = args[0];
		this.metric = Double.parseDouble(args[1]);
	}
	
	@Override
	public String toString() {
		return this.key + ":" + Double.toString(metric);
	}

	public double getMetric() {
		return metric;
	}

	public void setMetric(double metric) {
		this.metric = metric;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public int compareTo(Keyword anotherKeyword) {
		return (int) (this.metric - anotherKeyword.metric);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Keyword) {
			return false;
		}
		Keyword anotherKeyword = (Keyword) obj;
		return this.key == anotherKeyword.key;
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}
}