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
	
	public static void main(String[] args) {
		System.out
				.println(HTMLParser
						.getPlainText("<p><h3><strong>About Us:</strong></h3><p>Our society thrives when there is flow: plumbing, highways, electricity pylons, fiber optic cables and information.  The free-flow of utilities, goods, services, talent, and knowledge allows for stability and efficient decision-making.  Industry by industry, we have upgraded the basic infrastructure required for this kind of flow.  But one industry that has been dramatically left behind is global agriculture.  Within agriculture, blockages of information and knowledge create adverse consequences across the agricultural value chain.  Increased flow of capital is inhibited, policy decisions are reactive and often uninformed, robust logistics and distribution networks are nonexistent, and we have a vast mismatch of &#39;ground truths&#39;.  </p><p>Gro Intelligence is turning on the flow of information across the agricultural ecosystem by building products that change the way the world understands agriculture.  Through Clews, our web-based data platform, Gro offers the promise of aggregated, standardized, contextualized, analytics for every stakeholder -- from a commodities trader in New York to a policymaker in Ethiopia.  We are  building the needed knowledge infrastructure, that then allows for new agricultural businesses to emerge, unlocks innovation across the industry, and helps progress toward a future of cheap and abundant food for everyone.</p><p><br>Gro Intelligence is at an exciting time of hyper-growth with our US headquarters in New York City and international headquarters in Nairobi, Kenya. Our global team is smart, hard working, ambitious—and growing! We’re looking for outstanding and collaborative leaders to join our engineering team.</p><p><strong>Front-End Developer</strong></p><p>We are hiring a Front-End Developer to join the Gro Intelligence <u>US headquarters in New York City</u> to:</p><ul> <li> <p>Develop highly interactive, responsive web-based UI for dynamic “big data” visualization, analysis and modeling</p> </li> <li> <p>Work with Product Managers, UX/Interaction designers and backend software engineers to develop innovative UX flows and interactions</p> </li> <li> <p>Work with backend engineers to build appropriate web services to support the frontend</p> </li> <li> <p>Work with UX/interaction designers and product designers to deliver on the production vision.</p> </li> <li> <p>Test UI’s across multiple browsers and platforms</p> </li></ul><h3><strong>Relevant Experience and Skills:</strong></h3><ul> <li> <p>Creative, innovative web developer with an interest in UX and interaction</p> </li> <li> <p>Well-versed in modern UX flows, able to contribute to UX discussions with interaction and product designers</p> </li> <li> <p>Smart, critical thinker, can look at a UX challenge and conceptually grasp technical requirements</p> </li> <li> <p>Detail-oriented; an eye for design, but not necessarily a designer</p> </li> <li> <p>Uses clean, organized, sensible coding practices; uses sensible naming conventions</p> </li> <li> <p>Gets things done; delivers with finesse</p> </li> <li> <p>Experience with front end systems that deal with large amounts of data</p> </li> <li> <p>Experience with data visualizations and/or mapping</p> </li> <li> <p>Experience developing web applications</p> </li> <li> <p>Experience building web applications, start-to-finish</p> </li></ul><h3><strong>Technical Requirements:</strong></h3><ul> <li> <p>AngularJS/Javascript/jQuery (highly skilled)</p> </li> <li> <p>HTML/CSS, latest (highly skilled)</p> </li> <li> <p>Python (working knowledge)</p> </li> <li> <p>Version control (Git)</p> </li> <li> <p>Adobe photoshop, to work with mockups</p> </li> <li> <p>Responsive Design</p> </li> <li> <p>Web Services (RESTful)</p> </li></ul><h3><strong>Character traits we value:</strong></h3><ul> <li> <p>Smart, excellent problem solving skills</p> </li> <li> <p>Curious, love for turning ideas into reality</p> </li> <li> <p>Productive, self motivated</p> </li> <li> <p>Creative, collaborative, diligent</p> </li> <li> <p>Strong attention to detail with ability to understand the big picture</p> </li> <li> <p>Learner, interested in new skills and adapting to new technologies</p> </li> <li> <p>Ability to work with geographically distributed and culturally diverse team</p> </li></ul><h3><strong>Bonus Points:</strong></h3><ul> <li> <p>3+ years experience</p> </li> <li> <p>d3js</p> </li> <li> <p>svg/vector graphics</p> </li> <li> <p>Mapbox/TileMill/Leaflet</p> </li> <li> <p>JIRA/Atlassian</p> </li> <li> <p>Mobile/tablet web app development</p> </li> <li> <p>Segment.io</p> </li> <li>Mixpanel</li></ul><p>Speak to you soon!</p></p> "));
	}
}
