package controller;

import java.util.ArrayList;

import model.Graph;
import model.Node;

public class ControllerImpl {
	private Graph graph = new Graph();

	public void addWall(int r, int c) {
		graph.addWall(r, c);
	}

	public void removeWall(int r, int c) {
		graph.removeWall(r, c);
	}

	public void generateEdges() {
		graph.generateEdges();
	}

	public void clearEdges() {
		graph.clearEdges();
	}

	public void setSourceCordinates(int r, int c) {
		graph.setSourceCordinates(r, c);
	}

	public void setSinkCordinates(int r, int c) {
		graph.setSinkCordinates(r, c);
	}

	public ArrayList<Node> bfs() {
		return graph.bfs();
	}

	public ArrayList<Node> dfs() {
		return graph.dfs();

	}

	public ArrayList<Node> aStar() {
		return graph.aStar();
	}

}
