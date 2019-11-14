package test;

import api.TaggedVertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pa1.WebGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WebGraphTest {

	final String testVertices[] = {
			"A",
			"B",
			"C",
			"D"
	};

	final int testEdges[][] = {
			{0, 1},
			{0, 3},
			{1, 2},
			{2, 3},
			{3, 1}
	};

	final int testVertex = 3;

	WebGraph graph = new WebGraph("R");

	@BeforeEach
	void setUp() {
		for (int i = 0; i < testVertices.length; i++) {
			graph.addVertex(testVertices[i]);
		}
		for (int i = 0; i < testEdges.length; i++) {
			graph.addEdge(testEdges[i][0], testEdges[i][1]);
		}
	}

	@Test
	void addVertex() {
		String s = "New Vertex";
		graph.addVertex(s);
		assert graph.vertexData().get(graph.vertexData().size() - 1) == s;
	}

	@Test
	void addEdge() {
		// TODO
	}

	@Test
	void vertexData() {
		assertArrayEquals(testVertices, graph.vertexData().toArray());
	}

	@Test
	void vertexDataWithIncomingCounts() {
		int[] expectedIncomingCounts = new int[testVertices.length];
		List<TaggedVertex<String>> actualVertexDataWithIncomingCounts = graph.vertexDataWithIncomingCounts();
		for (int i = 0; i < testVertices.length; i++) {
			for (int[] testEdge : testEdges) {
				if (testEdge[1] == i) {
					expectedIncomingCounts[i]++;
				}
			}
		}
		for (int i = 0; i < actualVertexDataWithIncomingCounts.size(); i++) {
			assert actualVertexDataWithIncomingCounts.get(i).getTagValue() == expectedIncomingCounts[i];
			assert actualVertexDataWithIncomingCounts.get(i).getVertexData().equals(testVertices[i]);
		}
	}

	@Test
	void getNeighbors() {
		int v = testVertex;
		List<Integer> expectedNeighbors = new ArrayList<>();
		for (int i = 0; i < testEdges.length; i++) {
			if (testEdges[i][0] == v) {
				expectedNeighbors.add(testEdges[i][1]);
			}
		}
		List<Integer> actualNeighbors = graph.getNeighbors(v);
		assert actualNeighbors.equals(expectedNeighbors);
	}

	@Test
	void getIncoming() {
		int v = testVertex;
		List<Integer> expectedIncoming = new LinkedList<>();
		for (int i = 0; i < testEdges.length; i++) {
			if (testEdges[i][1] == v) {
				expectedIncoming.add(testEdges[i][0]);
			}
		}
		List<Integer> actualIncoming = graph.getIncoming(v);
		assert actualIncoming.equals(expectedIncoming);
	}
}