package masses;

public class FixedMass extends Mass{
	public FixedMass(String id, double x, double y, double mass) {
		super(id, x, y, mass);
	}
	
	@Override
	public void applyAllWorldForces () {
	    
	}

}
