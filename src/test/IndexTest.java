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
	String testQuery1 = "Donald",
			testQuery2 = "Duck";

	@BeforeEach
	void setUp() {
		List<TaggedVertex<String>> list = new ArrayList<>();
		try {
			FileReader fileReader = new FileReader(PROPERTIES_FILE);
			Properties properties = new Properties();
			properties.load(fileReader);

			Enumeration urls = properties.propertyNames();
			String key;
			while (urls.hasMoreElements()) {
				key = (String) urls.nextElement();
				list.add(new TaggedVertex<String>(key, Integer.parseInt(properties.getProperty(key))));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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