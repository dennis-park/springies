package forces;

import java.util.List;

import masses.Mass;

import org.jbox2d.common.Vec2;


public class WallRepulsion extends AbstractForce {
    protected double xdir;
    protected double ydir;
    private double angle;
    private int wallid;
    private static double WALL_DIRECTION = 90;
    private static double WALL_EXPONENT = 0.0;
    private List<Mass> mList;
    
    /**
     * This is a wall-repulsion force which applies a resistive force on masses inversely proportional to their
     * distance squared.
     * 
     * @param magnitude
     */
    public WallRepulsion (double magnitude, double exponent, int id, List<Mass> massList) {
        mMagnitude = magnitude;
        mExponent = exponent;
        mList = massList;
        wallid = id;
    }

    /**
     * Calculations of viscosity forces takes the x and y velocities as input and returns a Vec2
     * vector which can be used to calculate their effects of moving objects. 
     */
    @Override
    public Vec2 calculateForce (double x, double y) {
        return new Vec2((float) (mMagnitude / Math.pow(x, mExponent)), 0);
    }

    @Override
    public void setMagnitude (double magnitude) {
        this.mMagnitude = magnitude;
    }

    @Override
    public void setDirection (double direction) {
        this.mDirection = WALL_DIRECTION*(-1);
    }

    @Override
    public void setExponent (double exponent) {
        this.mExponent = WALL_EXPONENT;
    }

}
