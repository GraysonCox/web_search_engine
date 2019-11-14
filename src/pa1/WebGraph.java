package pa1;

import api.Graph;
import api.TaggedVertex;

import java.util.*;

/**
 * An implementation of Graph
 *
 * @author Grayson Cox
 */
public class WebGraph implements Graph<String> {

	private ArrayList<String> vertices;
	private ArrayList<LinkedList<Integer>> adjacencyLists;

	/**
	 * Constructs a WebGraph containing a single vertex for the given URL
	 *
	 * @param seedUrl The root URL of the graph
	 */
	public WebGraph(String seedUrl) {
		vertices = new ArrayList<>();
		vertices.add(seedUrl);
		adjacencyLists = new ArrayList<>();
		adjacencyLists.add(new LinkedList<>());
	}

	/**
	 * Adds the given vertex data as a new vertex with no edges
	 *
	 * @param v The data for the new vertex to be added
	 */
	public void addVertex(String v) {
		vertices.add(v);
		adjacencyLists.add(new LinkedList<>());
	}

	/**
	 * Adds an edge between the specified vertices and returns the index of the new vertex
	 *
	 * @param v The data for the new vertex to be added
	 * @return The index of the new vertex
	 */
	public int addVertexReturnIndex(String v) {
		vertices.add(v);
		adjacencyLists.add(new LinkedList<>());
		return vertices.size() - 1;
	}

	/**
	 * Adds an edge between the specified vertices
	 *
	 * @param u The index of the first vertex
	 * @param v The index of the second vertex
	 */
	public void addEdge(int u, int v) {
		if (u >= vertices.size() || v >= vertices.size()) {
			return; // This is good error handling.
		}
		adjacencyLists.get(u).add(v);
	}

	/**
	 * @return The number of vertices in the graph
	 */
	public int size() {
		return vertices.size();
	}

	@Override
	public ArrayList<String> vertexData() {
		return vertices;
	}

	@Override
	public ArrayList<TaggedVertex<String>> vertexDataWithIncomingCounts() {
		ArrayList<TaggedVertex<String>> taggedVertices = new ArrayList<>();
		int[] incomingCounts = new int[adjacencyLists.size()];
		incomingCounts[0]++; // Add 1 to the indegree of the seedUrl as per the project specification.
		for (List<Integer> adjList : adjacencyLists) {
			for (int i : adjList) {
				incomingCounts[i]++;
			}
		}
		for (int i = 0; i < adjacencyLists.size(); i++) {
			taggedVertices.add(new TaggedVertex<>(vertices.get(i), incomingCounts[i]));
		}
		return taggedVertices;
	}

	@Override
	public List<Integer> getNeighbors(int index) {
		return adjacencyLists.get(index);
	}

	@Override
	public List<Integer> getIncoming(int index) {
		List<Integer> incoming = new ArrayList<>();
		for (int u = 0; u < adjacencyLists.size(); u++) {
			for (int v : adjacencyLists.get(u)) {
				if (v == index) {
					incoming.add(u);
					break;
				}
			}
		}
		return incoming;
	}
}
