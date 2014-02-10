package forces;

import masses.Mass;
import org.jbox2d.common.Vec2;

public class Gravity implements Force {
    private static double GRAVITY_DIRECTION = Math.PI / 2; //90.0;
    private double mMagnitude;
	private double mExponent;
	private double mDirection;
	
    public Gravity (double magnitude) {
        this(magnitude, GRAVITY_DIRECTION);
    }

    public Gravity (double magnitude, double direction) {
        System.out.printf("Gravity created w/ mag = %.2f, direction = %.2f\n", magnitude, direction);
        mMagnitude = magnitude;
        mDirection = direction;
    }

    @Override
    public Vec2 calculateForce(Mass mass) {
        Vec2 gravity = new Vec2();
        float x_f = (float) (this.mMagnitude * Math.cos(mDirection));
        float y_f = (float) (this.mMagnitude * Math.sin(mDirection));
        
        // testGravityCalculations();
        gravity.set(x_f, y_f);
        return gravity;
    }
    
    private void testGravityCalculations() {
        float x_f = (float) (this.mMagnitude * Math.cos(mDirection));
        float y_f = (float) (this.mMagnitude * Math.sin(mDirection));
        System.out.printf("\tcos(%.2f) = %.2f, sin(%.2f) = %.2f\n", 
                          mDirection, Math.cos(mDirection), mDirection, Math.sin(mDirection));
        System.out.printf("\tGravity vector: mag = %.2f, angle = %.2f, x = %.2f, y = %.2f\n", 
                          this.mMagnitude, this.mDirection, x_f, y_f);
    }

	@Override
	public Vec2 calculateForce(double x, double y) {
		return null;
	}

	@Override
	public Vec2 calculateForce() {
		return null;
	}
    
}
