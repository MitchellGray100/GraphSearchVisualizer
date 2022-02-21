package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Node implements Comparable<Node> {
	ArrayList<Node> edges = new ArrayList<Node>();
	int row = -1;
	double estimatedDistance = 0;

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	int column = -1;
	boolean isRock = false;

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

	public Node(int r, int c, boolean rock) {
		row = r;
		column = c;
		isRock = rock;
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

	@Override
	public int compareTo(Node o) {
		if (estimatedDistance > o.estimatedDistance) {
			return 1;
		} else if (estimatedDistance == o.estimatedDistance) {
			return 0;
		} else {
			return -1;
		}
	}

	public double getEstimatedDistance() {
		return estimatedDistance;
	}

	public void setEstimatedDistance(double estimatedDistance) {
		this.estimatedDistance = estimatedDistance;
	}
}
