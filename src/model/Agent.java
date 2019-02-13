package model;

import java.awt.*;

public abstract class Agent {
	
	private int age;
	private int energy = 20;
	private Location location = null;
	private boolean alive = true;
	
	public void age() {
		age++;
	}
	
	public abstract void breed(Environment environment);
	public abstract Color getColor();
	public abstract void act(Environment environment);

	public Location getLocation() {
		return location;
	}

	public void degrade() {
		energy--;
		if(energy <= 0) {
			die();
		}
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public void die() {
		alive = false;
	}

	public boolean isAlive() {
		return alive && energy > 0;
	}

	public Agent setLocation(Location location) {
		this.location = location;
		return this;
	}
}
