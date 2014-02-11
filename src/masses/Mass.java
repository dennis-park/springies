package masses;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import springies.Constants;
import springs.Spring;
import jboxGlue.*;
import jgame.JGObject;


/**
 * This class represents the Mass objects in our Spring-Mass assemblies.
 * 
 * This element moves in response to the forces applied by the springs connected to it.
 * 
 * In the data file, this mass is indicated by the keyword mass followed by its id, its
 * x- and y-coordinates, an initial x- and y-velocites, and a mass. If the velocities
 * are not given, the default value should be 0. If the mass is not given, the default
 * value should be 1.
 * 
 * Radius for all masses will be 10 and default collision
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 */
public class Mass extends PhysicalObjectCircle {
    protected ArrayList<Spring> mSpringList = new ArrayList<Spring>();

    /**
     * Mass constructor with defined member variables
     * 
     * @param mass_id
     * @param x_pos
     * @param y_pos
     * @param init_vel_x
     * @param init_vel_y
     * @param mass
     */
    public Mass (String mass_id,
                 double x_pos,
                 double y_pos,
                 double init_vel_x,
                 double init_vel_y,
                 double mass) {
        super(mass_id, Constants.COLLISION_ID, Constants.DEFAULT_COLOR, Constants.DEFAULT_RADIUS,
              mass);
        setPos(x_pos, y_pos);
        Vec2 initial_velocity = new Vec2((float) init_vel_x, (float) init_vel_y);
        this.myBody.setLinearVelocity(initial_velocity);
    }

    /**
     * Mass constructor with defined member variables
     * 
     * @param mass_id
     * @param x_pos
     * @param y_pos
     * @param init_vel_x
     * @param init_vel_y
     */
    public Mass (String mass_id, double x_pos, double y_pos, double init_vel_x, double init_vel_y) {
        this(mass_id, x_pos, y_pos, init_vel_x, init_vel_y, 1);
    }

    /**
     * Mass constructor with defined member variables
     * 
     * @param mass_id
     * @param x_pos
     * @param y_pos
     * @param mass
     */
    public Mass (String mass_id, double x_pos, double y_pos, double mass) {
        this(mass_id, x_pos, y_pos, Constants.DEFAULT_XVEL, Constants.DEFAULT_YVEL, mass);
    }

    /**
     * Mass constructor with defined member variables
     * 
     * @param mass_id
     * @param x_pos
     * @param y_pos
     */
    public Mass (String mass_id, double x_pos, double y_pos) {
        this(mass_id, x_pos, y_pos, Constants.DEFAULT_XVEL, Constants.DEFAULT_YVEL,
             Constants.DEFAULT_MASS);
    }

    /**
     * Mass constructor with defined member variables
     * 
     * @param mass_id
     */
    public Mass (String mass_id) {
        this(mass_id, Constants.DEFAULT_XPOS, Constants.DEFAULT_YPOS, Constants.DEFAULT_XVEL,
             Constants.DEFAULT_YVEL, Constants.DEFAULT_MASS);
    }

    @Override
    /**
     * On-hit collisions are to be ignored.
     * @param other
     */
    public void hit (JGObject other)
    {
        if (other.getName() == "wall") {
            // we hit something! bounce off it!
            Vec2 velocity = myBody.getLinearVelocity();
            // is it a tall wall?
            final double DAMPING_FACTOR = 1;
            boolean isSide = other.getBBox().height > other.getBBox().width;
            if (isSide) {
                velocity.x *= -DAMPING_FACTOR;
            }
            else {
                velocity.y *= -DAMPING_FACTOR;
            }
            // apply the change
            myBody.setLinearVelocity(velocity);
        }
    }
    /**
     * Mass moves based on superclass.
     */
    public void move () {
        super.move();
    }

    /**
     * Mass connects to specified spring and its own private list of springs 
     * will be updated.
     * @param spring
     */
    public void connectSpring (Spring spring) {
        mSpringList.add(spring);
    }

    /**
     * Apply specified force vector
     * @param force
     */
    public void applyForceVector (Vec2 force) {
        myBody.applyForce(force, myBody.m_xf.position);
    }
}
