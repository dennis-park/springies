package masses;

public class FixedMass extends Mass{
	public FixedMass(double x, double y, double mass) {
		super(x, y, mass);
	}
	
	@Override
	public void applyAllWorldForces () {}

}
