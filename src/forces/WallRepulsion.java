package forces;

import org.jbox2d.common.Vec2;

public class WallRepulsion extends AbstractForce {
    private static double TOP = 90.0;
    private static double RIGHT = 0.0;
    private static double BOTTOM = 270.0;
    private static double LEFT = 180.0;
    
    /**
     * This is a wall-repulsion force which applies a resistive force on masses inversely proportional to their
     * distance squared.
     * 
     * @param magnitude
     */
    public WallRepulsion (int id, double magnitude, double exponent) {
        mMagnitude = magnitude;
        mExponent = exponent;

        switch (id) {
            case 1:
                this.mDirection = TOP;
            case 2:
                this.mDirection = RIGHT;
            case 3:
                this.mDirection = BOTTOM;
            case 4:
                this.mDirection = LEFT;
        }
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
