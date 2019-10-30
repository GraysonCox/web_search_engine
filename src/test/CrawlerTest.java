package test;

import api.Graph;
import api.TaggedVertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pa1.Crawler;

import java.util.List;

class CrawlerTest {

	final String PROPERTIES_FILE = "VertexDataWithIncomingCounts.properties";

	final String testUrl = "https://www.spacejam.com/archive/spacejam/movie/jam.html";
	final int maxDepth = 15;
	final int maxPages = 150;

	Crawler crawler;

	@BeforeEach
	void setUp() {
		crawler = new Crawler(testUrl, maxDepth, maxPages);
	}

	@Test
	void Crawl() {
		Graph graph = crawler.crawl();
		List<TaggedVertex<String>> urls = graph.vertexDataWithIncomingCounts();
		TestUtils.writeToPropertiesFile(urls, PROPERTIES_FILE);
	}
}