package model;

public abstract class Fish extends Agent {

	public abstract Class<? extends Agent> getPrey();

	@Override
	public void act(Environment e) {
		if(isAlive()) {
			Agent prey = e.getNeighbour(this, getPrey());
			if(prey != null) {
				eat(e);
			} else {
				swim(e);
			}
			breed(e);
		}
	}

	public void swim(Environment e) {
		// values will need to be driven by something
		// e.g. eat food, breed or swim around etc.
		Location freeLocation = e.getFreeLocation(this);
		e.updateAgentLocation(this, freeLocation);
		degrade();
	}

	public void elevate() {
		setEnergy(getEnergy() + 1);
	}

	public void eat(Environment e) {
		Agent prey = e.getNeighbour(this, getPrey());
		prey.die();
		e.updateAgentLocation(this, prey.getLocation());
		elevate();
	}

}
