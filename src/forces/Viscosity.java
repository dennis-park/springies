package forces;

import org.jbox2d.common.Vec2;

public class Viscosity extends Force {
    protected double xdir;
    protected double ydir;

    /**
     * This is a viscous drag force which applies a resistive force on masses proportional to their
     * velocity. In the data file, this force is indicated by the keyword viscosity followed by a
     * scale value
     * 
     * @param magnitude
     */
    public Viscosity (double magnitude) {
        this.mMagnitude = magnitude;
    }

    /**
     * Calculations of viscosity forces takes the x and y velocities as input and returns a Vec2
     * vector which can be used to calculate their effects of moving objects.
     */
    public Vec2 calculateForce (double x_velocity, double y_velocity) {
        float x_f = (float) (x_velocity);
        float y_f = (float) (y_velocity);

        Vec2 viscosity = new Vec2(x_f, y_f);
        float scalar = (float) (viscosity.length() * this.mMagnitude);

        viscosity.normalize();

        return viscosity.mul(scalar);
    }
}
