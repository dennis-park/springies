package springs;

import masses.Mass;

public class Muscle extends Spring{

	private double mAmplitude;
	private double mLength;
	private static final int mPeriod = 60;
	
	public Muscle(Mass start, Mass end, double amplitude) {
		super(start, end);
		mAmplitude = amplitude;
		mLength = super.getLength();
	}
	
	
	

}
