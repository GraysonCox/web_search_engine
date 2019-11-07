package pa1;

import api.Graph;
import api.TaggedVertex;
import api.Util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
	public Graph<String> crawl() { // TODO: Make this more efficient.
		AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();
		Queue<TaggedVertex<String>> queue = new LinkedList<>();
		List<String> discovered = new ArrayList<>();

		graph.addVertex(seedUrl);
		queue.add(new TaggedVertex<>(seedUrl, 0));
		discovered.add(seedUrl);

		TaggedVertex<String> currentPage;
		List<String> links;
		int linkIndex, depth = 0;
		while (!queue.isEmpty() && depth <= maxDepth) {
			currentPage = queue.remove();
			links = webService.getLinksFromPage(currentPage.getVertexData());
			for (String link : links) {
				if (Util.ignoreLink(currentPage.getVertexData(), link)) {
					continue;
				}
				if (!discovered.contains(link)) {
					discovered.add(link);
					linkIndex = graph.addVertexReturnIndex(link);
					graph.addEdge(currentPage.getTagValue(), linkIndex);
					queue.add(new TaggedVertex<>(link, linkIndex));
				} else {
					linkIndex = graph.indexOf(link);
					if (!graph.getNeighbors(currentPage.getTagValue()).contains(linkIndex)) { // TODO: Refactor
						graph.addEdge(currentPage.getTagValue(), linkIndex);
					}
				}
				if (graph.vertexData().size() == maxPages) {
					return graph;
				}
			}
			depth++; // TODO: I don't think this is right.
		}
		return graph;
	}
}
