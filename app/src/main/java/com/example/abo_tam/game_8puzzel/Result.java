package com.example.abo_tam.game_8puzzel;

public class Result {
	private State state; 
	private Node solution;
	
	public Result(State state) {
		this.state = state;
	}
	
	public Result(State state, Node solution) {
		this.state = state;
		this.solution = solution;
	}
	
	public String result(){
		String result = new String("");
		while(solution.getAction() != null){
			result = solution.getAction().toString() + " - " + result;
			solution = solution.getParent();
		}
		return result;
	}

	public Node getSulotion() {
		return solution;
	}

	public void setSulotion(Node solution) {
		this.solution = solution;
	}

	public State getState() {
		return state;
	}
	
	
	
	
}
