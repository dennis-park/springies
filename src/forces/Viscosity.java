package forces;

import masses.Mass;
import org.jbox2d.common.Vec2;


public class Viscosity implements Force {
    /**
     * This is a viscous drag force which applies a resistive force on masses proportional to their
     * velocity. In the data file, this force is indicated by the keyword viscosity followed by a
     * scale value
     * 
     * @param magnitude
     */
    private double mMagnitude;
    private double mDirection;

    public Viscosity (double magnitude) {
        this.mMagnitude = magnitude;
    }

    /**
     * Calculations of viscosity forces takes the x and y velocities as input and returns a Vec2
     * vector which can be used to calculate their effects of moving objects.
     */
    public Vec2 calculateForce (Mass mass) {
        double x_velocity = mass.getBody().getLinearVelocity().x;
        double y_velocity = mass.getBody().getLinearVelocity().y;
        float x_f = (float) (-1 * x_velocity);
        float y_f = (float) (-1 * y_velocity);

        Vec2 viscosity = new Vec2(x_f, y_f);
        float scalar = (float) (viscosity.length() * this.mMagnitude);

        viscosity.normalize();

        // testViscosityCalculations(mass, viscosity, scalar);
        return viscosity.mul(scalar);
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
