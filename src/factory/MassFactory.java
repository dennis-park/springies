package factory;

import masses.Mass;
import springies.Springies;

public class MassFactory extends Factory{

	public MassFactory(Springies s) {
		super(s);
		// TODO Auto-generated constructor stub
	}
	public Mass create (String id,
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

}
