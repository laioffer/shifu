package model;

/**
 * A helper class to convert HTML to text. 
 */
public class HTMLParser {

	/**
	 * Convert HTML to plain text 
	 */
	public static String getPlainText(String htmlTxt) {
	    String plainText = htmlTxt.replaceAll("\\<.*?>","");
	    return replaceSpecialChar(plainText);
	}
	

	/**
	 * Replace special character such as ", /, '
	 */
	public static String replaceSpecialChar(String str) {
		return str.replace("\"", "\\\"").replace("/", " or ").replace("'", "");
	}
}
