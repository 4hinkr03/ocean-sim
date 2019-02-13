package model;

import java.awt.*;

public class Sardine extends Fish {

	@Override
	public void breed(Environment environment) {
		Location breedLocation = environment.getFreeLocation(this );
		if(breedLocation != null && getEnergy() > 5) {
			if(Environment.randomInt(0,100) < 5) {
				environment.addAgent(new Sardine().setLocation(breedLocation));
				degrade();
			}
		}
	}

	@Override
	public Class<? extends Agent> getPrey() {
		return Plankton.class;
	}

	@Override
	public Color getColor() {
		return Color.MAGENTA;
	}
}