package pa1;

import api.Graph;
import api.TaggedVertex;

import java.util.*;

public class AdjacencyListGraph<E> implements Graph<E> {

	private ArrayList<E> vertices;
	private ArrayList<LinkedList<Integer>> adjacencyLists;

	public AdjacencyListGraph() {
		vertices = new ArrayList<>();
		adjacencyLists = new ArrayList<>();
	}

	public void addVertex(E v) {
		vertices.add(v);
		adjacencyLists.add(new LinkedList<>());
	}

	public int addVertexReturnIndex(E v) {
		vertices.add(v);
		adjacencyLists.add(new LinkedList<>());
		return vertices.size() - 1;
	}

	public void addEdge(int u, int v) {
		if (u >= vertices.size() || v >= vertices.size()) {
			return; // TODO: Better error handling
		}
		adjacencyLists.get(u).add(v);
	}

	public int indexOf(E v) {
		return vertices.indexOf(v);
	}

	// MARK - Graph<E>

	@Override
	public ArrayList<E> vertexData() {
		return vertices;
	}

	@Override
	public ArrayList<TaggedVertex<E>> vertexDataWithIncomingCounts() { // TODO: Refactor
		ArrayList<TaggedVertex<E>> taggedVertices = new ArrayList<>();
		int incomingCounts[] = new int[adjacencyLists.size()];
		for (List<Integer> adjList : adjacencyLists) {
			for (int i : adjList) {
				incomingCounts[i]++;
			}
		}
		for (int i = 0; i < adjacencyLists.size(); i++) {
			taggedVertices.add(new TaggedVertex<E>(vertices.get(i), incomingCounts[i]));
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
		for (int i = 0; i < adjacencyLists.size(); i++) {
			for (int j = 0; j < adjacencyLists.get(i).size(); j++) { // TODO: Refactor, improve efficiency
				if (adjacencyLists.get(i).get(j) == index) {
					incoming.add(i);
				}
			}
		}
		return incoming;
	}
}
