package forces;

import org.jbox2d.common.Vec2;


public class Viscosity extends AbstractForce {
    protected double xdir;
    protected double ydir;

    /**
     * This is a viscous drag force which applies a resistive force on masses proportional to their
     * velocity.In the data file, this force is indicated by the keyword viscosity followed by a
     * scale value
     * 
     * @param magnitude
     */
    public Viscosity (double magnitude) {
        mMagnitude = magnitude;
    }

    /**
     * Calculations of viscosity forces takes the x and y velocities as input and returns a Vec2
     * vector which can be used to calculate their effects of moving objects. 
     */
    public Vec2 calculateForce (double x, double y) {
        Vec2 viscosity = new Vec2();
        
        // TO DO: NEED TO CALCULATE THE DIRECTION OF THE VISCOSITY FORCE!!! 
        float x_f = (float) (x * this.mMagnitude);
        float y_f = (float) (y * this.mMagnitude);
        viscosity.set(x_f, y_f);
        return viscosity;
    }
}
