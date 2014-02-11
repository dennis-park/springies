package forces;

import masses.Mass;
import org.jbox2d.common.Vec2;
import springies.Constants;
import walls.Wall;


public class WallRepulsion implements Force {
    private Wall mWall;
    private int mWallId;

    private double mMagnitude;
    private double mExponent;
    private double mDirection;

    /**
     * This is a wall-repulsion force which applies a resistive force on masses inversely
     * proportional to their
     * distance squared.
     * 
     * @param magnitude
     */
    public WallRepulsion (Wall wall, double magnitude, double exponent) {
        mMagnitude = magnitude;
        mExponent = exponent;
        mWall = wall;
        mWallId = wall.getWallId();
        mDirection = wall.getWallDirection();
    }

    public WallRepulsion (Wall wall) {
        this(wall, Constants.DEFAULT_WALL_REPULSION_MAGNITUDE, Constants.DEFAULT_EXPONENT);
    }

    public void setWall (Wall wall) {
        if (wall == null) { throw new RuntimeException("Wall given to setWall was null"); }
        if (wall.getWallId() != this.mWallId) { throw new RuntimeException(
                                                                           "Wall given to setWall has the wrong wallId"); }
        this.mWall = wall;
    }

    /**
     * Calculations of WallRepulsion forces takes the x and y position as input and returns a Vec2
     * vector which can be used to calculate the effects of wall repulsion.
     */
    public Vec2 calculateForce (Mass mass) {
        double x_pos = mass.getBody().getPosition().x;
        double y_pos = mass.getBody().getPosition().y;
        float x_distance = (float) Math.abs(mWall.x - x_pos);
        float y_distance = (float) Math.abs(mWall.y - y_pos);

        /*
         * This calculation works because y_distance will be negative for bottom walls and
         * positive for top walls. x_distance will be negative for right walls and positive for
         * left walls.
         */
        float x_f = (float) (x_distance * Math.cos(this.mDirection));
        float y_f = (float) (y_distance * Math.sin(this.mDirection));
        Vec2 force = new Vec2(x_f, y_f);

        float scalar = (float) (this.mMagnitude * 1 / (Math.pow(force.length(), this.mExponent)));

        force.normalize();
        return force.mul(scalar);
    }

    @Override
    public Vec2 calculateForce (double x, double y) {
        return null;
    }

    @Override
    public Vec2 calculateForce () {
        return null;
    }

    public int getWallId () {
        return mWallId;
    }

    public String getWallIdString () {
        return String.format("%d", mWallId);
    }
}
