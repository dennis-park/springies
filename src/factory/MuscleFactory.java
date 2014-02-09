package factory;

import masses.Mass;
import springies.Springies;
import springs.Muscle;

public class MuscleFactory extends Factory{
	
	public MuscleFactory(Springies s) {
		super(s);
		mMasses = s.getMassMap();
	}
	public Muscle create (String m1_id, String m2_id, String restlength, String amplitude) {
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
