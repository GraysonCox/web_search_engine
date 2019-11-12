package pa1;

import api.Graph;
import api.TaggedVertex;

import java.util.*;

/**
 * An implementation of Graph
 *
 * @param <E> The data associated with each vertex
 * @author Grayson Cox
 */
public class AdjacencyListGraph<E> implements Graph<E> {

	private ArrayList<E> vertices;
	private ArrayList<LinkedList<Integer>> adjacencyLists;

	/**
	 * Constructs an empty AdjacencyListGraph
	 */
	public AdjacencyListGraph() {
		vertices = new ArrayList<>();
		adjacencyLists = new ArrayList<>();
	}

	/**
	 * Adds the given vertex data as a new vertex with no edges
	 *
	 * @param v The data for the new vertex to be added
	 */
	public void addVertex(E v) {
		vertices.add(v);
		adjacencyLists.add(new LinkedList<>());
	}

	/**
	 * Adds an edge between the specified vertices and returns the index of the new vertex
	 *
	 * @param v The data for the new vertex to be added
	 * @return The index of the new vertex
	 */
	public int addVertexReturnIndex(E v) {
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
	public ArrayList<E> vertexData() {
		return vertices;
	}

	@Override
	public ArrayList<TaggedVertex<E>> vertexDataWithIncomingCounts() {
		ArrayList<TaggedVertex<E>> taggedVertices = new ArrayList<>();
		int[] incomingCounts = new int[adjacencyLists.size()];
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
