package forces;

import masses.Mass;
import org.jbox2d.common.Vec2;
import springies.Constants;


public class Gravity implements Force {
    private double mMagnitude;
    private double mDirection;

    public Gravity (double magnitude) {
        this(magnitude, Constants.DEFAULT_GRAVITY_DIRECTION);
    }

    public Gravity (double magnitude, double direction) {
        mMagnitude = magnitude;
        mDirection = direction;
    }

    @Override
    public Vec2 calculateForce (Mass mass) {
        Vec2 gravity = new Vec2();
        float x_f = (float) (this.mMagnitude * Math.cos(mDirection));
        float y_f = (float) (this.mMagnitude * Math.sin(mDirection));

        // testGravityCalculations();
        gravity.set(x_f, y_f);
        return gravity;
    }

    @Override
    public Vec2 calculateForce (double x, double y) {
        return null;
    }

    @Override
    public Vec2 calculateForce () {
        return null;
    }

}
