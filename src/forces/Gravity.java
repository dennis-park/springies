package forces;

import masses.Mass;
import org.jbox2d.common.Vec2;

public class Gravity extends Force {

    private static double GRAVITY_DIRECTION = 90;

    public Gravity (double magnitude) {
        this(magnitude, GRAVITY_DIRECTION);
    }

    public Gravity (double magnitude, double direction) {
        mMagnitude = magnitude;
        mDirection = direction;
    }

    @Override
    public Vec2 calculateForce(Mass mass) {
        Vec2 gravity = new Vec2();
        float x_f = (float) (this.mMagnitude * Math.cos(mDirection));
        float y_f = (float) (this.mMagnitude * Math.sin(mDirection));
        gravity.set(x_f, y_f);
        return gravity;
    }
    
}
