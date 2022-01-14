package model;

import java.util.ArrayList;

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

	/**
	 * Sets the position at r,c to null (creates a wall).
	 * 
	 * @param r The row position of the wall
	 * @param c The column position of the wall
	 */
	public void addWall(int r, int c) {
		graph[r][c] = null;
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
		if (r > rows || c > columns || r < 0 || c < 0)
			return false;

		if (graph[r][c] == null)
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
				if (graph[r][c] != null) {
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
	 * Sets the coordinate of the source node to r,c
	 * 
	 * @param r row cord of source node.
	 * @param c col cord of source node.
	 */
	public void setSourceCordinates(int r, int c) {
		if (sourceStartRow != -1 && sourceStartColumn != -1) {
			graph[sourceStartRow][sourceStartColumn] = new Node(sourceStartRow, sourceStartColumn);
		}
		sourceStartRow = r;
		sourceStartColumn = c;
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
		sinkEndRow = r;
		sinkEndColumn = c;
	}

	/**
	 * Calculates the bfs route from the source to sink.
	 * 
	 * @return list The list of each node visit in order.
	 */
	public ArrayList<Node> bfs() {
		ArrayList<Node> list = new ArrayList<Node>();
		return list;
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
}
