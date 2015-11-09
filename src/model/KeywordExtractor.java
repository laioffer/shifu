package model;

import java.util.ArrayList;
import java.util.Collection;
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

	public List<String> extract(String description) {
		List<String> keywords = new ArrayList<>();

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
				if (mv.metric >= 0.12) {
					keywords.add(mv.value.text);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keywords;
	}
}
