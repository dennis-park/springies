package forces;

import org.jbox2d.common.Vec2;

public class WallRepulsion extends AbstractForce {
    private String mWallId;
    
    /**
     * This is a wall-repulsion force which applies a resistive force on masses inversely proportional to their
     * distance squared.
     * 
     * @param magnitude
     */
    public WallRepulsion (double magnitude, double exponent, String id) {
        mMagnitude = magnitude;
        mExponent = exponent;
        mWallId = id;
    }

    /**
     * Calculations of viscosity forces takes the x and y velocities as input and returns a Vec2
     * vector which can be used to calculate their effects of moving objects. 
     */
    public Vec2 calculateForce (double x, double y) {
        
        // TO DO: WALL REPULSION IS CALCULATED WRONG
        return new Vec2((float) (mMagnitude / Math.pow(x, mExponent)), 0);
    }
}
