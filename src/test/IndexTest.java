package test;

import api.TaggedVertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pa1.Index;

import java.util.List;

class IndexTest {

	final String TEST_DATA_FILE = "src/test/test_data/VertexDataWithIncomingCounts.properties";

	String testQuery1 = "vanilla",
			testQuery2 = "chicken";

	Index index;

	@BeforeEach
	void setUp() {
		List<TaggedVertex<String>> list = TestUtils.readFromPropertiesFile(TEST_DATA_FILE);
		index = new Index(list);
		index.makeIndex();
	}

	@Test
	void search() {
		List<TaggedVertex<String>> rankedList = index.search(testQuery1);
		TestUtils.printTaggedVertexList(rankedList);
	}

	@Test
	void searchWithAnd() {
		List<TaggedVertex<String>> rankedList = index.searchWithAnd(testQuery1, testQuery2);
		TestUtils.printTaggedVertexList(rankedList);
	}

	@Test
	void searchWithOr() {
		List<TaggedVertex<String>> rankedList = index.searchWithOr(testQuery1, testQuery2);
		TestUtils.printTaggedVertexList(rankedList);
	}

	@Test
	void searchAndNot() {
		List<TaggedVertex<String>> rankedList = index.searchAndNot(testQuery1, testQuery2);
		TestUtils.printTaggedVertexList(rankedList);
	}
}