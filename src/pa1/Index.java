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
	private Map<String, Integer> inDegreesForUrl;
	private Map<String, Map<String, Integer>> listW;
	private Comparator<TaggedVertex> rankComparator = (o1, o2) -> o2.getTagValue() - o1.getTagValue();

	/**
	 * Constructs an index from the given list of urls.  The
	 * tag value for each url is the indegree of the corresponding
	 * node in the graph to be indexed.
	 *
	 * @param urls information about graph to be indexed
	 */
	public Index(List<TaggedVertex<String>> urls) {
		webService = WebService.getInstance();
		inDegreesForUrl = new HashMap<>();
		listW = new HashMap<>();
		for (TaggedVertex<String> v : urls) {
			inDegreesForUrl.put(v.getVertexData(), v.getTagValue());
		}
	}

	/**
	 * Creates the index.
	 */
	public void makeIndex() {
		String bodyText, word;
		Scanner scanner;
		for (String currentPage : inDegreesForUrl.keySet()) { // Iterate through pages.
			bodyText = webService.getBodyFromPage(currentPage);
			scanner = new Scanner(bodyText);
			while (scanner.hasNext()) { // Iterate through words in page.
				word = scanner.next();
				word = Util.stripPunctuation(word);
				if (Util.isStopWord(word) || word.isEmpty()) {
					continue;
				}
				if (listW.containsKey(word)) { // The word has been found at least once already
					int oldCount = listW.get(word).getOrDefault(currentPage, 0);
					listW.get(word).put(currentPage, oldCount + 1);
				} else { // The word has not been found previously
					listW.put(word, new HashMap<>());
					listW.get(word).put(currentPage, 1);
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
		w = Util.stripPunctuation(w);
		Map<String, Integer> pagesContainingW = listW.get(w);
		if (pagesContainingW == null) {
			return new ArrayList<>();
		}
		SortedSet<TaggedVertex<String>> rankedList = new TreeSet<>(rankComparator);
		int rank;
		for (Map.Entry<String, Integer> urlTuple : pagesContainingW.entrySet()) {
			if (urlTuple.getValue() == 0) { // TODO: Is this necessary?
				continue;
			}
			rank = inDegreesForUrl.get(urlTuple.getKey()) * urlTuple.getValue();
			rankedList.add(new TaggedVertex<>(urlTuple.getKey(), rank));
		}
		return new ArrayList<>(rankedList); // TODO: Make sure this is efficient.
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
		w1 = Util.stripPunctuation(w1);
		w2 = Util.stripPunctuation(w2);
		Map<String, Integer> pagesContainingW1 = listW.get(w1),
				pagesContainingW2 = listW.get(w2);
		if (pagesContainingW1 == null || pagesContainingW2 == null) {
			return new ArrayList<>();
		}
		SortedSet<TaggedVertex<String>> rankedList = new TreeSet<>(rankComparator);
		int rank, w2Count;
		for (Map.Entry<String, Integer> urlTuple : pagesContainingW1.entrySet()) {
			w2Count = pagesContainingW2.getOrDefault(urlTuple.getKey(), 0);
			if (w2Count == 0) {
				continue;
			}
			rank = inDegreesForUrl.get(urlTuple.getKey()) * (urlTuple.getValue() + w2Count);
			rankedList.add(new TaggedVertex<>(urlTuple.getKey(), rank));
		}
		return new ArrayList<>(rankedList);
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
		w1 = Util.stripPunctuation(w1);
		w2 = Util.stripPunctuation(w2);
		Map<String, Integer> pagesContainingW1 = listW.get(w1),
			pagesContainingW2 = listW.get(w2);
		if (pagesContainingW1 == null) {
			return new ArrayList<>();
		}
		SortedSet<TaggedVertex<String>> rankedList = new TreeSet<>(rankComparator);
		int rank;
		for (Map.Entry<String, Integer> urlTuple : pagesContainingW1.entrySet()) {
			if (pagesContainingW2.containsKey(urlTuple.getKey())) {
				continue;
			}
			rank = inDegreesForUrl.get(urlTuple.getKey()) * urlTuple.getValue();
			rankedList.add(new TaggedVertex<>(urlTuple.getKey(), rank));
		}
		return new ArrayList<>(rankedList); // TODO: Make sure this is efficient.
	}
}
