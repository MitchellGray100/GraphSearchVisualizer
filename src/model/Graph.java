package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Graph {

	private final int rows = 20;
	private final int columns = 20;
	private int sourceStartRow = -1;
	private int sourceStartColumn = -1;
	private int sinkEndRow = -1;
	private int sinkEndColumn = -1;

	Node[][] graph = new Node[rows][columns];

	/**
	 * Creates graph by setting each node to a new Node.
	 */
	public Graph() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				graph[r][c] = new Node(r, c);
			}
		}
	}

	public boolean isReadyToSearch() {
		if (sourceStartRow != -1 && sourceStartColumn != -1 && sinkEndRow != -1 && sinkEndColumn != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Sets the position at r,c to null (creates a wall).
	 * 
	 * @param r The row position of the wall
	 * @param c The column position of the wall
	 */
	public void addWall(int r, int c) {
		graph[r][c] = new Node(r, c, true);
	}

	/**
	 * Sets the position at r,c to a Node (removes a wall)
	 * 
	 * @param r The row to remove the wall
	 * @param c The column to remove the wall
	 */
	public void removeWall(int r, int c) {
		graph[r][c] = new Node(r, c);
	}

	/**
	 * Helper method for SafeAddEdge. Checks to see if the row and column of the
	 * node is invalid or a wall.
	 * 
	 * @param r Row of the node
	 * @param c Col of the node
	 * @return Whether or not the node is a valid node.
	 */
	public boolean checkEdge(int r, int c) {
		if (r >= rows || c >= columns || r < 0 || c < 0)
			return false;

		if (graph[r][c].isRock)
			return false;

		return true;
	}

	/**
	 * Helper function for safeAddEdge. Checks to see if the given node is valid. If
	 * so, it adds it as an edge to the r,c node.
	 * 
	 * @param r Row of node wanting to add edge.
	 * @param c Col of node wanting to add edge.
	 * @param x Row of node to be added.
	 * @param y Col of node to be added.
	 */
	public void safeAddEdge(int r, int c, int x, int y) {
		if (checkEdge(x, y)) {
			graph[r][c].addEdge(graph[x][y]);
		}
	}

	/**
	 * Generates Edges for each node. Walls don't get any edges.
	 */
	public void generateEdges() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				if (!graph[r][c].isRock) {
					safeAddEdge(r, c, r + 1, c);
					safeAddEdge(r, c, r, c + 1);
					safeAddEdge(r, c, r - 1, c);
					safeAddEdge(r, c, r, c - 1);

				}
			}
		}
	}

	/**
	 * Clears all edges of all node.
	 */
	public void clearEdges() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				if (graph[r][c] != null) {
					graph[r][c].clearEdges();
				}
			}
		}
	}

	/**
	 * Replaces all nodes with new nodes.
	 */
	public void clear() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				graph[r][c] = new Node(r, c);
			}
		}
	}

	/**
	 * Sets the coordinate of the source node to r,c
	 * 
	 * @param r row cord of source node.
	 * @param c col cord of source node.
	 */
	public void setSourceCordinates(int r, int c) {
		if (sourceStartRow != -1 && sourceStartColumn != -1) {
			graph[sourceStartRow][sourceStartColumn] = new Node(sourceStartRow, sourceStartColumn);
		}
		if (r != -1 && c != -1)
			graph[r][c] = new Node(r, c);// incase of wall

		sourceStartRow = r;
		sourceStartColumn = c;
	}

	public int getSourceXCordinate() {
		return sourceStartRow;
	}

	public int getSourceYCordinate() {
		return sourceStartColumn;
	}

	public int getSinkXCordinate() {
		return sinkEndRow;
	}

	public int getSinkYCordinate() {
		return sinkEndColumn;
	}

	/**
	 * Sets the coordinate of the sink node to r,c.
	 * 
	 * @param r row cord of sink node.
	 * @param c col cord of sink node.
	 */
	public void setSinkCordinates(int r, int c) {
		if (sinkEndRow != -1 && sinkEndColumn != -1) {
			graph[sinkEndRow][sinkEndColumn] = new Node(sinkEndRow, sinkEndColumn);
		}
		if (r != -1 && c != -1)
			graph[r][c] = new Node(r, c);// incase of wall

		sinkEndRow = r;
		sinkEndColumn = c;
	}

	/**
	 * Calculates the bfs route from the source to sink.
	 * 
	 * @return list The list of each node visit in order.
	 */
	public ArrayList<Node> bfs() {
		Node s = graph[sourceStartRow][sourceStartColumn];
		Node t = graph[sinkEndRow][sinkEndColumn];
		ArrayList<Node> list = new ArrayList<Node>();
		Node temp = s;
		// Mark all the vertices as not visited(By default
		// set as false)
		HashSet<Node> visited = new HashSet<Node>();

		// Create a queue for BFS
		LinkedList<Node> queue = new LinkedList<Node>();

		// Mark the current node as visited and enqueue it
		visited.add(temp);
		queue.add(temp);

		while (queue.size() != 0) {
			// Dequeue a vertex from queue and print it

			temp = queue.poll();
			if (temp.equals(t)) {
				return list;
			}
			if (!temp.equals(s)) {
				list.add(temp);
			}
			System.out.print(temp + " ");

			// Get all adjacent vertices of the dequeued vertex s
			// If a adjacent has not been visited, then mark it
			// visited and enqueue it
			Iterator<Node> i = temp.keySet();
			while (i.hasNext()) {
				Node n = i.next();
				if (!visited.contains(n)) {
					visited.add(n);
					queue.add(n);
				}
			}
		}
		return null;
	}

	/**
	 * Calculates the dfs route from the source to sink.
	 * 
	 * @return list The list of each node visit in order.
	 */
	public ArrayList<Node> dfs() {
		ArrayList<Node> list = new ArrayList<Node>();
		return list;
	}

	/**
	 * Calculates the aStar route from the source to sink.
	 * 
	 * @return list The list of each node visit in order.
	 */
	public ArrayList<Node> aStar() {
		ArrayList<Node> list = new ArrayList<Node>();
		return list;
	}

	public int getRowSize() {
		return rows;
	}

	public int getColumnSize() {
		return columns;
	}
}
