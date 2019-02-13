package model;

import java.awt.*;

public class Shark extends Fish {

	@Override
	public void breed(Environment environment) {
		Location breedLocation = environment.getFreeLocation(this );
		if(breedLocation != null && getEnergy() > 5) {
			if(Environment.randomInt(0,100) < 5) {
				environment.addAgent(new Shark().setLocation(breedLocation));
				degrade();
			}
		}
	}

	@Override
	public Class<? extends Agent> getPrey() {
		return Sardine.class;
	}

	@Override
	public Color getColor() {
		return Color.WHITE;
	}
}
