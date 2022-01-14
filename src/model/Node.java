package model;

import java.util.HashSet;
import java.util.Iterator;

public class Node {
	HashSet<Node> edges = new HashSet<Node>();
	int row = -1;
	int column = -1;

	/**
	 * Constructor of a Node
	 * 
	 * @param r row value of the node
	 * @param c col value of the node
	 */
	public Node(int r, int c) {
		row = r;
		column = c;
	}

	/**
	 * Adds edge to edges
	 * 
	 * @param edge The edge to add
	 */
	public void addEdge(Node edge) {
		edges.add(edge);
	}

	/**
	 * Removes edge from edges
	 * 
	 * @param edge The edge to remove
	 */
	public void removeEdge(Node edge) {
		edges.remove(edge);
	}

	/**
	 * Clears all edges from edges
	 */
	public void clearEdges() {
		edges.clear();
	}

	/**
	 * Allows for iterating through each edge.
	 * 
	 * @return all eges as an iterator.
	 */
	public Iterator<Node> keySet() {
		return edges.iterator();
	}
}
