package pa1;

import java.util.*;

import api.TaggedVertex;
import api.Util;

/**
 * Implementation of an inverted index for a web graph.
 *
 * @author Grayson Cox
 */
public class Index {

	private WebService webService;
	private List<TaggedVertex<String>> urls;
	private List<String> words;
	private Map<String, Map<String, Integer>> listW;

	/**
	 * Constructs an index from the given list of urls.  The
	 * tag value for each url is the indegree of the corresponding
	 * node in the graph to be indexed.
	 *
	 * @param urls information about graph to be indexed
	 */
	public Index(List<TaggedVertex<String>> urls) {
		this.urls = urls;
		webService = WebService.getInstance();
		words = new ArrayList<>();
		listW = new HashMap<>();
	}

	/**
	 * Creates the index.
	 */
	public void makeIndex() {
		String bodyText, word;
		Scanner scanner;
		for (TaggedVertex<String> currentPage : urls) { // Iterate through pages.
			bodyText = webService.getBodyFromPage(currentPage.getVertexData());
			scanner = new Scanner(bodyText);
			while (scanner.hasNext()) { // Iterate through words in page.
				word = scanner.next();
				word = Util.stripPunctuation(word);
				if (Util.isStopWord(word) || word.isEmpty()) {
					continue;
				}
				if (listW.containsKey(word)) { // The word has been found at least once already
					int oldCount = listW.get(word).getOrDefault(currentPage.getVertexData(), 0);
					listW.get(word).put(currentPage.getVertexData(), oldCount + 1);
				} else { // The word has not been found previously
					words.add(word);
					listW.put(word, new HashMap<>());
					listW.get(word).put(currentPage.getVertexData(), 1);
				}
			}
		}
	}

	/**
	 * Searches the index for pages containing keyword w.  Returns a list
	 * of urls ordered by ranking (largest to smallest).  The tag
	 * value associated with each url is its ranking.
	 * The ranking for a given page is the number of occurrences
	 * of the keyword multiplied by the indegree of its url in
	 * the associated graph.  No pages with rank zero are included.
	 *
	 * @param w keyword to search for
	 * @return ranked list of urls
	 */
	public List<TaggedVertex<String>> search(String w) {
		// TODO
		return null;
	}


	/**
	 * Searches the index for pages containing both of the keywords w1
	 * and w2.  Returns a list of qualifying
	 * urls ordered by ranking (largest to smallest).  The tag
	 * value associated with each url is its ranking.
	 * The ranking for a given page is the number of occurrences
	 * of w1 plus number of occurrences of w2, all multiplied by the
	 * indegree of its url in the associated graph.
	 * No pages with rank zero are included.
	 *
	 * @param w1 first keyword to search for
	 * @param w2 second keyword to search for
	 * @return ranked list of urls
	 */
	public List<TaggedVertex<String>> searchWithAnd(String w1, String w2) {
		// TODO
		return null;
	}

	/**
	 * Searches the index for pages containing at least one of the keywords w1
	 * and w2.  Returns a list of qualifying
	 * urls ordered by ranking (largest to smallest).  The tag
	 * value associated with each url is its ranking.
	 * The ranking for a given page is the number of occurrences
	 * of w1 plus number of occurrences of w2, all multiplied by the
	 * indegree of its url in the associated graph.
	 * No pages with rank zero are included.
	 *
	 * @param w1 first keyword to search for
	 * @param w2 second keyword to search for
	 * @return ranked list of urls
	 */
	public List<TaggedVertex<String>> searchWithOr(String w1, String w2) {
		// TODO
		return null;
	}

	/**
	 * Searches the index for pages containing keyword w1
	 * but NOT w2.  Returns a list of qualifying urls
	 * ordered by ranking (largest to smallest).  The tag
	 * value associated with each url is its ranking.
	 * The ranking for a given page is the number of occurrences
	 * of w1, multiplied by the
	 * indegree of its url in the associated graph.
	 * No pages with rank zero are included.
	 *
	 * @param w1 first keyword to search for
	 * @param w2 second keyword to search for
	 * @return ranked list of urls
	 */
	public List<TaggedVertex<String>> searchAndNot(String w1, String w2) {
		// TODO
		return null;
	}
}
