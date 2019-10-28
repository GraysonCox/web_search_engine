package test;

import api.Graph;
import api.TaggedVertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pa1.Crawler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CrawlerTest {

    final String testUrl = "https://www.spacejam.com/archive/spacejam/movie/jam.html";
    final int maxDepth = 15;
    final int maxPages = 25;

    Crawler crawler;

    @BeforeEach
    void setUp() {
        crawler = new Crawler(testUrl, maxDepth, maxPages);
    }

    @Test
    void Crawl() {
        Graph graph = crawler.crawl();
        List<TaggedVertex<String>> urls = graph.vertexDataWithIncomingCounts();
        for (TaggedVertex v : urls) {
            System.out.println(v.getVertexData() + ", " + v.getTagValue());
        }
    }
}