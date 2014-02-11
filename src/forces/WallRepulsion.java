package forces;

import masses.Mass;
import org.jbox2d.common.Vec2;
import walls.Wall;


public class WallRepulsion implements Force {
    public static final double DEFAULT_WALL_REPULSION_MAGNITUDE = 0.1;
    public static final double DEFAULT_EXPONENT = 2.0;
    public static final double TOP_DIRECTION = Math.PI / 2;
    public static final double RIGHT_DIRECTION = 0.0;
    public static final double BOTTOM_DIRECTION = Math.PI / 2;
    public static final double LEFT_DIRECTION = 0.0;

    public static final int TOP_ID = 1;
    public static final int RIGHT_ID = 2;
    public static final int BOTTOM_ID = 3;
    public static final int LEFT_ID = 4;

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

        System.out.printf("Wall Repulsion created for wall #%d w/ mag = %.2f, exp = %.2f ",
                          mWallId, magnitude, exponent);
        System.out.printf("at JGAME (%.2f, %.2f) \t JBOX (%.2f, %.2f) \n", wall.x, wall.y, wall
                .getBody().getPosition().x, wall.getBody().getPosition().y);
    }

    // private void setWallDirection(int wall_id) {
    // if (wall_id == TOP_ID) {
    // this.mDirection = TOP_DIRECTION;
    // }
    // else if (wall_id == RIGHT_ID) {
    // this.mDirection = RIGHT_DIRECTION;
    // }
    // else if (wall_id == BOTTOM_ID) {
    // this.mDirection = BOTTOM_DIRECTION;
    // }
    // else if (wall_id == LEFT_ID) {
    // this.mDirection = LEFT_DIRECTION;
    // }
    // System.out.printf("Wall ID = %d, angle = %.2f\n", wall_id, this.mDirection);
    // }

    public WallRepulsion (Wall wall) {
        this(wall, DEFAULT_WALL_REPULSION_MAGNITUDE, DEFAULT_EXPONENT);
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
        // System.out.printf("Distance to wall: %.2f, exp = %.2f, scalar = %.2f",
        // force.length(), this.mExponent, scalar);
        //
        force.normalize();

        // testWallRepulsion(mass, force, scalar);
        return force.mul(scalar);
    }

    private void testWallRepulsion (Mass mass, Vec2 force, float scalar) {
        System.out
                .printf("\tCalculating wall force: Position=(%.2f, %.2f), Wall direction=%d, angle=%.2f\n",
                        mass.getBody().getPosition().x, mass.getBody().getPosition().y,
                        this.mWallId, this.mDirection);
        Vec2 calc = force.mul(scalar);
        System.out.printf("\tforce=<%.2f, %.2f>, Scalar=%.2f, Total Force = <%.2f, %.2f>\n\n",
                          force.x, force.y, scalar, calc.x, calc.y);
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
