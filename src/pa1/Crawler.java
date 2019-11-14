package pa1;

import api.Graph;
import api.TaggedVertex;
import api.Util;

import java.util.*;

/**
 * Implementation of a basic web crawler that creates a graph of some
 * portion of the world wide web.
 *
 * @author Grayson Cox
 */
public class Crawler {

	private final String seedUrl;
	private final int maxDepth;
	private final int maxPages;
	private WebService webService;

	/**
	 * Constructs a Crawler that will start with the given seed url, including
	 * only up to maxPages pages at distance up to maxDepth from the seed url.
	 *
	 * @param seedUrl
	 * @param maxDepth
	 * @param maxPages
	 */
	public Crawler(String seedUrl, int maxDepth, int maxPages) {
		this.seedUrl = seedUrl;
		this.maxDepth = maxDepth;
		this.maxPages = maxPages;
		this.webService = WebService.getInstance();
	}

	/**
	 * Creates a web graph for the portion of the web obtained by a BFS of the
	 * web starting with the seed url for this object, subject to the restrictions
	 * implied by maxDepth and maxPages.
	 *
	 * @return an instance of Graph representing this portion of the web
	 */
	public Graph<String> crawl() {
		WebGraph webGraph = new WebGraph(seedUrl); // Initialize webGraph with seedUrl
		List<List<TaggedVertex<String>>> layers = new ArrayList<>();
		layers.add(new LinkedList<>()); // Initialize empty list L[0]
		int layerCounter = 0; // Initialize layer counter to 0
		Map<String, Integer> discoveredUrlsWithIndices = new HashMap<>(); // Initialize empty hash table to store (url, index)

		layers.get(layerCounter).add(new TaggedVertex<>(seedUrl, 0)); // Add (seedUrl, 0) to L[0]
		discoveredUrlsWithIndices.put(seedUrl, 0); // Add (seedUrl, 0) to discoveredUrlsWithIndices

		while (!layers.get(layerCounter).isEmpty() && layerCounter <= maxDepth) { // While L[i] isn't empty and i<=maxDepth
			layers.add(new LinkedList<>()); // Initialize empty list L[i+1]
			for (TaggedVertex<String> currentPage : layers.get(layerCounter)) { // For each page in L[i]
				List<String> linksFromCurrentPage = webService.getLinksFromPage(currentPage.getVertexData());
				for (String link : linksFromCurrentPage) { // For each link in current page
					if (Util.ignoreLink(currentPage.getVertexData(), link)) { // Ignore link if necessary
						continue;
					}
					int linkIndex = discoveredUrlsWithIndices.getOrDefault(link, -1);
					if (linkIndex == -1) { // If link hasn't been discovered
						linkIndex = webGraph.addVertexReturnIndex(link); // Add to webGraph
						webGraph.addEdge(currentPage.getTagValue(), linkIndex); // Add edge to webGraph
						discoveredUrlsWithIndices.put(link, linkIndex); // Add to set of discovered vertices
						layers.get(layerCounter + 1).add(new TaggedVertex<>(link, linkIndex)); // Add to L[i+1]
					} else { // If link has been discovered
						if (!webGraph.getNeighbors(currentPage.getTagValue()).contains(linkIndex)) {
							webGraph.addEdge(currentPage.getTagValue(), linkIndex); // Add edge if not already added
						}
					}
					if (webGraph.size() == maxPages) { // Return if page limit has been reached
						return webGraph;
					}
				}
			}
			layerCounter++; // Increment layer counter
		}
		return webGraph;
	}
}
