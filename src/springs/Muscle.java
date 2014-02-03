package springs;

import masses.Mass;

public class Muscle extends Spring{

	private double mAmplitude;
	private double mLength;
	private static final int DEFAULT_PERIOD = 60;
	private int mCount;
	
	public Muscle(Mass m1, Mass m2, double amplitude) {
		super(m1, m2);
		mAmplitude = amplitude;
		mLength = super.getLength();
	}
	
	public Muscle(Mass m1, Mass m2, double length, double k, double amplitude) {
		super(m1, m2, k);
		mAmplitude = amplitude;
		mLength = length;
	}
	
	@Override
	public void move() {
		setLength(mLength + mAmplitude
				* Math.cos((mCount*Math.PI) / (DEFAULT_PERIOD/2)));
		mCount ++;
	}

}