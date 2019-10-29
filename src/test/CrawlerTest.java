package test;

import api.Graph;
import api.TaggedVertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pa1.Crawler;

import java.util.List;

class CrawlerTest {

    final String PROPERTIES_FILE = "VertexDataWithIncomingCounts.properties";

//    final String testUrl = "https://www.spacejam.com/archive/spacejam/movie/jam.html/";
//    final String testUrl = "https://www.reddit.com/";
//    final String testUrl = "https://en.wikipedia.org/wiki/Internet_meme";
    final String testUrl = "https://en.wikipedia.org/wiki/Hank_Hill";
    final int maxDepth = 100;
    final int maxPages = 100;

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