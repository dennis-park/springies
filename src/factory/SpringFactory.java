package factory;

import masses.Mass;
import springies.Springies;
import springs.Spring;

public class SpringFactory extends Factory{

	public SpringFactory(Springies s) {
		super(s);
	}
	private Spring create (String m1_id, String m2_id, String restlength, String constant) {
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

}
