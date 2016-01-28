package com.example.abo_tam.game_8puzzel;

public class Node {
	private Problem problem;
	private Node child;
	private Node parent;
	private Action action;
	
	public Node(Problem problem, Node parent) {
		this.problem = problem;
		this.parent = parent;
	}
	public Node(Problem problem, Node parent, Action action) {
		this.problem = problem;
		this.parent = parent;
		this.action = action;
	}
	
	public Node(Problem problem) {
		this.problem = problem;
	}
	
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	public Node getChild() {
		return child;
	}
	public void setChild(Node child) {
		this.child = child;
	}
	public int[][] state(){
		return problem.getState();
	}
	
	public static Node childNode(Problem problem, Node parent, Action action){
		return new Node(problem, parent, action);
	}
	
	public int getNumberOfActions(){
		return problem.getNumberOfActions();
	} 
	
	public Action getAction(){
		return action;
	}
	
	
	
}
