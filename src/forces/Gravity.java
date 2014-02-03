package forces;

import org.jbox2d.common.Vec2;


public class Gravity extends AbstractForce {

    private static double GRAVITY_DIRECTION = 90;
    private static double GRAVITY_EXPONENT = 0.0;

    public Gravity (double magnitude) {
        this(magnitude, GRAVITY_DIRECTION, GRAVITY_EXPONENT);
    }

    public Gravity (double magnitude, double direction, double exponent) {
        mMagnitude = magnitude;
        mDirection = direction;
        mExponent = exponent;

        xdir = Math.cos(direction);
        ydir = Math.sin(direction);
    }

    @Override
    public Vec2 calculateForce (double x, double y) {
        Vec2 gravity = new Vec2();
        float x_f = (float) (x * this.mMagnitude);
        float y_f = (float) (y * this.mMagnitude);
        gravity.set(x_f, y_f);
        return gravity;
    }

    @Override
    public void setMagnitude (double magnitude) {
        this.mMagnitude = magnitude;

    }

    @Override
    public void setDirection (double direction) {
        this.mDirection = GRAVITY_DIRECTION;
    }

    @Override
    public void setExponent (double exponent) {
        this.mExponent = GRAVITY_EXPONENT;
    }

    @Override
    public void toggleForce () {
        if (!forceOn) {
            forceOn = true;
        }
        else {
            forceOn = false;
        }
    }

}
