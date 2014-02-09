package factory;

import masses.FixedMass;
import springies.Springies;

public class FixedMassFactory extends Factory{

	public FixedMassFactory(Springies s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	public FixedMass create (String id, String x, String y, String mass) {
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

        return new FixedMass(mass_id, x_pos, y_pos, obj_mass);
    }

    


}
