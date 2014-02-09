package factory;

import java.util.ArrayList;
import java.util.HashMap;

import masses.FixedMass;
import masses.Mass;
import springies.Assembly;
import springies.Springies;
import springs.Muscle;
import springs.Spring;

public class Factory {
	protected Springies mSpringies;
	protected ArrayList<Spring> mSprings;

	private static final String MASS = "mass";
	private static final String SPRING = "spring";
	private static final String MUSCLE = "muscle";

	// map with mass IDs
	private HashMap<String, Mass> mMasses = new HashMap<String, Mass>();

	private Assembly mAssembly = new Assembly();
	
	public Factory (Springies s) {
		mSpringies = s;
		mMasses = s.getMassMap();
		mSprings = s.getSpringsList();
	}
	
	
	public void makeFixedMass (String id, String x, String y, String mass) {
		if (id == null || x == null || y == null) {
			System.out.println("Mass not properly created");
		}
		String mass_id = id;
		double x_pos = Double.parseDouble(x);
		double y_pos = Double.parseDouble(y);
        double obj_mass = 0.0;
        if (mass != null) {
            obj_mass = Double.parseDouble(mass);
        }

        mAssembly.add(new FixedMass(mass_id, x_pos, y_pos, obj_mass));
    }

    public Mass makeMass (String id,
                               String x,
                               String y,
                               String init_x,
                               String init_y,
                               String mass) {
        if (id == null || x == null || y == null) {
            System.out.println("Mass not properly created");
        }
        String mass_id = id;
        double x_pos = Double.parseDouble(x);
        double y_pos = Double.parseDouble(y);

        if (init_x == null && init_y == null && mass == null) { return new Mass(mass_id, x_pos,
                                                                                y_pos); }
        if (init_x == null && init_y == null) { return new Mass(mass_id, x_pos, y_pos,
                                                                Double.parseDouble(mass)); }
        if (mass == null) { return new Mass(mass_id, x_pos, y_pos, Double.parseDouble(init_x),
                                            Double.parseDouble(init_y)); }
        return new Mass(mass_id, x_pos, y_pos, Double.parseDouble(init_x),
                        Double.parseDouble(init_y), Double.parseDouble(mass));
    }

    private Spring makeSpring (String m1_id, String m2_id, String restlength, String constant) {
        Mass m1 = mMasses.get(m1_id);
        Mass m2 = mMasses.get(m2_id);
        
        Spring newSpring;
        if (restlength == null && constant == null) { 
            newSpring = new Spring(m1, m2); 
        }
        if (constant == null) { 
            newSpring = new Spring(m1, m2, Double.parseDouble(restlength)); 
        }
        newSpring = new Spring(m1, m2, Double.parseDouble(restlength), Double.parseDouble(constant));
        m1.connectSpring(newSpring);
        m2.connectSpring(newSpring);
        return newSpring;
    }

    private Muscle makeMuscle (String m1_id, String m2_id, String restlength, String amplitude) {
        Mass m1 = mMasses.get(m1_id);
        Mass m2 = mMasses.get(m2_id);
        double amp = Double.parseDouble(amplitude);

        Muscle newMuscle; 
        if (restlength == null) { 
            newMuscle = new Muscle(m1, m2, amp); 
        }
        newMuscle = new Muscle(m1, m2, Double.parseDouble(restlength), amp);
        m1.connectSpring(newMuscle);
        m2.connectSpring(newMuscle);
        return newMuscle;
    }

}
