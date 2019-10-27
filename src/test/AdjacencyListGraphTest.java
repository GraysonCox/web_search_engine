package test;

import api.TaggedVertex;
import pa1.AdjacencyListGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListGraphTest {

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

	AdjacencyListGraph<String> graph = new AdjacencyListGraph();

	@org.junit.jupiter.api.BeforeEach
	void setUp() {
		for (int i = 0; i < testVertices.length; i++) {
			graph.addVertex(testVertices[i]);
		}
		for (int i = 0; i < testEdges.length; i++) {
			graph.addEdge(testEdges[i][0], testEdges[i][1]);
		}
	}

	@org.junit.jupiter.api.Test
	void addVertex() {
		String s = "New Vertex";
		graph.addVertex(s);
		assert graph.vertexData().get(graph.vertexData().size() - 1) == s;
	}

	@org.junit.jupiter.api.Test
	void addEdge() {
		// TODO
	}

	@org.junit.jupiter.api.Test
	void vertexData() {
		assertArrayEquals(testVertices, graph.vertexData().toArray());
	}

	@org.junit.jupiter.api.Test
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

	@org.junit.jupiter.api.Test
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

	@org.junit.jupiter.api.Test
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