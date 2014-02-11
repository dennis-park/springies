package masses;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import springies.Constants;
import springs.Spring;
import jboxGlue.*;
import jgame.JGColor;
import jgame.JGObject;


public class Mass extends PhysicalObjectCircle {
    protected ArrayList<Spring> mSpringList = new ArrayList<Spring>();

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

    public Mass (String mass_id, double x_pos, double y_pos, double init_vel_x, double init_vel_y) {
        this(mass_id, x_pos, y_pos, init_vel_x, init_vel_y, 1);
    }

    public Mass (String mass_id, double x_pos, double y_pos, double mass) {
        this(mass_id, x_pos, y_pos, Constants.DEFAULT_XVEL, Constants.DEFAULT_YVEL, mass);
    }

    public Mass (String mass_id, double x_pos, double y_pos) {
        this(mass_id, x_pos, y_pos, Constants.DEFAULT_XVEL, Constants.DEFAULT_YVEL,
             Constants.DEFAULT_MASS);
    }

    public Mass (String mass_id) {
        this(mass_id, Constants.DEFAULT_XPOS, Constants.DEFAULT_YPOS, Constants.DEFAULT_XVEL,
             Constants.DEFAULT_YVEL, Constants.DEFAULT_MASS);
    }

    @Override
    public void hit (JGObject other) {
    }

    public void move () {
        super.move();
    }

    public void connectSpring (Spring spring) {
        mSpringList.add(spring);
    }

    /**
     * Apply specified force vector
     * 
     */
    public void applyForceVector (Vec2 force) {
        myBody.applyForce(force, myBody.m_xf.position);
    }
}
