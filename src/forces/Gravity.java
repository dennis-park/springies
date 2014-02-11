package forces;

import masses.Mass;
import org.jbox2d.common.Vec2;
import springies.Constants;


/**
 * This is a constant force in a given direction (usually down). The magnitude of the force due to
 * gravity is proportional to the mass of an element. In the data file, this force is indicated by
 * the keyword gravity followed by a direction (in degrees) and a magnitude (in pixels per second)
 * 
 * This class will add to the existing Gravity set by the JBox game. In order for the simulation to
 * work properly, the developer must set the JBox World gravity to <0, 0>
 * 
 * @author Thanh-Ha Nguyen
 * 
 */
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
    public Vec2 calculateForce () {
        return null;
    }

}
