package factory;

import java.util.ArrayList;
import java.util.HashMap;

import masses.FixedMass;
import masses.Mass;
import springies.Assembly;
import springies.Springies;
import springs.Muscle;
import springs.Spring;

public abstract class Factory {
	protected Springies mSpringies;
	
	public Factory (Springies s) {
		mSpringies = s;
	}

	protected abstract void processModel(String id);
	
}
