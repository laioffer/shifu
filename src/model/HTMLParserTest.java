package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class HTMLParserTest {

	@Test
	public void parseHTMLsucceeds() {
		String plainText = HTMLParser.getPlainText("<p><h3><strong>About Us:</strong></h3><p>");
		assertEquals("About Us:", plainText);
		assertTrue(plainText.equals("About Us"));
	}
	
	@Test
	public void parseHTMLfails() {
		try {
			//some methods that are easy to fail.
		    fail( "My method didn't throw when I expected it to" );
		} catch (Exception expectedException) {
			//verify the details of your exception.
		}
	}

}
