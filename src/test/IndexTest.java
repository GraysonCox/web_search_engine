package test;

import api.TaggedVertex;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pa1.Index;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

class IndexTest {

	final String PROPERTIES_FILE = "VertexDataWithIncomingCounts.properties";

	Index index;
	String testQuery1 = "Propane",
			testQuery2 = "Charcoal";

	@BeforeEach
	void setUp() {
		List<TaggedVertex<String>> list = TestUtils.readFromPropertiesFile(PROPERTIES_FILE);
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