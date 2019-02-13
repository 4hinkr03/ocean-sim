package model;

import java.awt.*;

public class Plankton extends Agent {

	@Override
	public void breed(Environment environment) {
		Location breedLocation = environment.getFreeLocation(this );
		if(breedLocation != null && getEnergy() > 5) {
			if(Environment.randomInt(0,100) < 8) {
				environment.addAgent(new Plankton().setLocation(breedLocation));
				degrade();
			}
		}
	}

	@Override
	public void act(Environment e) {
		breed(e);
		degrade();
	}

	@Override
	public Color getColor() {
		return Color.GREEN;
	}
}
