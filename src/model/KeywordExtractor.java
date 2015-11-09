package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import com.sharethis.textrank.MetricVector;
import com.sharethis.textrank.TextRank;

public class KeywordExtractor {

	private static TextRank textRank = null;

	public KeywordExtractor(ServletContext context) {

		String fullPath = context
				.getRealPath("/WEB-INF/res");
		try {
			textRank = new TextRank(fullPath, "en");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Keyword> extract(String description) {
		HashSet<Keyword> keywords = new HashSet<>();

		try {
			textRank.prepCall(description, true);
			// wrap the call in a timed task
			final FutureTask<Collection<MetricVector>> task = new FutureTask<Collection<MetricVector>>(
					textRank);
			Collection<MetricVector> answer = null;

			final Thread thread = new Thread(task);
			thread.run();

			answer = task.get(10000L, TimeUnit.MILLISECONDS); // timeout in 15 s

			for (MetricVector mv : answer) {
				if (mv.metric >= TextRank.MIN_NORMALIZED_RANK) {
					// Merge duplicate keys as well.
					keywords.add(new Keyword(mv.value.text, mv.metric));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Convert the set of keywords to a sorted list.
		List<Keyword> sortedKeywords = new ArrayList<>(keywords);
		Collections.sort(sortedKeywords);
		return sortedKeywords;
	}
}
