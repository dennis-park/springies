package forces;

import masses.Mass;
import org.jbox2d.common.Vec2;
import springies.Constants;
import walls.Wall;


/**
 * <p>
 * This class implements the wall repulsion force which which applies a resistive force on masses
 * inversely proportional to their distance squared.repels (or attracts) Masses in Springies away
 * from or towards the wall. This class will work only with a live and valid Wall object. If the
 * Wall object or its JBox component is removed in the course of the game, the WallRepulsion force
 * will fail.
 * </p>
 * <p>
 * The force is a wall repulsion force that repels masses from a given wall. This does not replace
 * bouncing, if a mass is moving fast enough to overcome the force and reach the wall, it should
 * still bounce. In the data file, this force is indicated by the keyword wall followed by a wall ID
 * (1 is the top, 2 is right, etc.), a magnitude, and an exponent value. An exponent value of 2.0
 * means inverse-square force (the force is inversely proportional to the distance squared). A value
 * of 0.0 is a constant force independent of position.
 * </p>
 * <p>
 * WallRepulsion forces and their associated walls are constructed in EnvironmentManager but can be
 * called directly on a Mass.
 * </p>
 * 
 * @author Thanh-Ha Nguyen
 * 
 */
public class WallRepulsion implements Force {
    private Wall mWall;
    private int mWallId;

    private double mMagnitude;
    private double mExponent;
    private double mDirection;

    /**
     * <p>
     * Wall repulsion constructor takes a wall, magnitude and exponent.
     * </p>
     * <p>
     * Wall my_top_wall = new Wall(1);
     * </p>
     * <p>
     * WallRepulsion my_top_wall_force = new WallRepulsion(my_top_wall, 50.0, 1.2);
     * </p>
     * 
     * @param wall
     * @param magnitude
     * @param exponent
     */
    public WallRepulsion (Wall wall, double magnitude, double exponent) {
        mMagnitude = magnitude;
        mExponent = exponent;
        mWall = wall;
        mWallId = wall.getWallId();
        mDirection = wall.getWallDirection();
    }

    /**
     * If no exponent is given, WallRepulsion will construct a wall with a default exponent (see
     * springies.Constants.java)
     * 
     * @param wall
     * @param magnitude
     */
    public WallRepulsion (Wall wall, double magnitude) {
        this(wall, magnitude, Constants.DEFAULT_EXPONENT);
    }

    /**
     * If no exponent or magnitude is given is given, WallRepulsion will construct a wall with
     * default magnitude and default exponent (see springies.Constants.java)
     * 
     * @param wall
     */
    public WallRepulsion (Wall wall) {
        this(wall, Constants.DEFAULT_WALL_REPULSION_MAGNITUDE, Constants.DEFAULT_EXPONENT);
    }

    /**
     * The object in the Springies simulation can be set during the simulation. This will remove the
     * current wall (assuming that nothing is depending on the current wall) and set a new Wall to
     * be associated with this instance of WallRepulsion.
     * 
     * @param wall
     */
    public void setWall (Wall wall) {
        mWall.remove();
        if (wall == null) { throw new RuntimeException("Wall given to setWall was null"); }
        mWall = wall;
        mWallId = wall.getWallId();
    }

    /**
     * <p>
     * Calculations of WallRepulsion forces takes the mass that that WallRepulsion force needs to
     * act on as input and returns a Vec2 vector which can be used to calculate the effects of wall
     * repulsion. This method returns a Vec2 Object which will then need to be appled (if
     * appropriate to the force)
     * </p>
     * 
     * <p>
     * Wall force is an orthogonal force to a wall and is directly related to the magnitude of the
     * wall and inversely related to the distance of the Mass object to the Wall to a power (defined
     * by exponenet)
     * 
     * @param mass
     */
    public Vec2 calculateForce (Mass mass) {
        if (mWall == null) { throw new RuntimeException(
                                                        "Wall associated with this force is not valid"); }
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
    /**
     * If calculate force is not given an argument it will return a <0,0> force Vec2
     */
    public Vec2 calculateForce () {
        return Constants.ZERO_VECTOR;
    }
}
