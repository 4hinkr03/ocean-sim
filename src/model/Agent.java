package model;

public abstract class Agent {
	
	private int age;
	
	public void age() {
		age++;
	}
	
	public abstract void breed();
}
