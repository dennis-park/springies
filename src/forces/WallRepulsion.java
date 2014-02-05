package forces;

import org.jbox2d.common.Vec2;
import walls.Wall;


public class WallRepulsion extends Force {
    private static final double TOP = 90.0;
    private static final double RIGHT = 0.0;
    private static final double BOTTOM = 90.0;
    private static final double LEFT = 0.0;

    private Wall mWall;
    private int mWallId;

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

        switch (wall.getWallId()) {
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
     * Parser can make a WallRepulsion force without making a wall object. Known
     * dependency: If the parser makes a WallRepulsion force, you must explicitly
     * call setWall();
     * 
     * @param wall_id
     * @param magnitude
     * @param exponent
     */
    public WallRepulsion (int wall_id, double magnitude, double exponent) {
        mMagnitude = magnitude;
        mExponent = exponent;

        switch (wall_id) {
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
    public Vec2 calculateForce (double x_pos, double y_pos) {
        float x_distance = (float) (x_pos - this.mWall.x);
        float y_distance = (float) (this.mWall.y - y_pos);

        /*
         * This calculation works because y_distance will be negative for bottom walls and
         * positive for top walls. x_distance will be negative for right walls and positive for
         * left walls.
         */
        float x_f = (float) (x_distance * Math.cos(this.mDirection));
        float y_f = (float) (y_distance * Math.sin(this.mDirection));
        Vec2 force = new Vec2(x_f, y_f);

        float scalar = (float) (Math.pow(force.length(), this.mExponent));

        return force.mul(scalar);
    }
}
